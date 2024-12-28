package com.w83ll43.alliance.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.w83ll43.alliance.common.model.entity.Api;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.common.service.InnerApiService;
import com.w83ll43.alliance.common.service.InnerUserApiInvokeService;
import com.w83ll43.alliance.common.service.InnerUserService;
import com.w83ll43.alliance.sdk.constant.SDKConstant;
import com.w83ll43.alliance.sdk.enums.HttpMethod;
import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import com.w83ll43.alliance.sdk.utils.ApiRequestMaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final Long FIVE_MINUTES = 60 * 5L;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerApiService innerApiService;

    @DubboReference
    private InnerUserApiInvokeService innerUserApiInvokeService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1、用户发送请求到 API 网关

        // 2、请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("标识 {}, {} 请求, {}, 参数 {}, 来自 {}!",
                request.getId(), request.getMethod(), request.getPath(),
                request.getQueryParams(), request.getRemoteAddress());

        HttpHeaders headers = request.getHeaders();
        if ("api-alliance-web-front".equals(headers.getFirst(SDKConstant.X_CA_REQUEST_FROM))) {
            // TODO 判断 X-Ca-Request-id 是否存在 Redis 中
            return chain.filter(exchange);
        }

        // 3、TODO 黑白名单

        // 4、用户鉴权（判断 ak、sk 是否合法）
        String appKey = headers.getFirst(SDKConstant.X_CA_KEY);
        String sign = headers.getFirst(SDKConstant.X_CA_SIGNATURE);
        String timestamp = headers.getFirst(SDKConstant.X_CA_TIMESTAMP);
        String nonce = headers.getFirst(SDKConstant.X_CA_NONCE);

        User user = innerUserService.getUserByAccessKey(appKey);
        String appSecret = user.getSecretKey();

        ServerHttpResponse response = exchange.getResponse();
        // 5、TODO 判断接口是否存在
        Api api = innerApiService.getApiByPathAndMethod(request.getPath().value(), request.getMethod().name());

        // 6、验证签名是否有效
        if (StrUtil.hasEmpty(appKey, sign, nonce, timestamp)) {
            log.info("标识 {}, 必要请求头不存在！", request.getId());
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }
        // 验证时间戳是否过期
        long currentTime = System.currentTimeMillis() / 1000;
        if (timestamp != null && currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            log.info("标识 {}, 重放请求!", request.getId());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }



        // 获取重新生成的签名
        String signature = reGenSign(request, timestamp, nonce, appKey, appSecret);

        if (!signature.equals(sign)) {
            log.info("标识 {}, 签名验证失败!", request.getId());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        log.info("标识 {}, 签名验证成功!", request.getId());
        return handleResponse(exchange, chain, user, api);
    }

    private static String reGenSign(ServerHttpRequest request, String timestamp, String nonce, String appKey, String appSecret) {
        String path = request.getPath().value();
        ApiRequest apiRequest = new ApiRequest(HttpMethod.valueOf(request.getMethod().name()), path);

        // 无需生成随机数
        apiRequest.setHost(request.getURI().getHost());
        apiRequest.setPath(path);
        apiRequest.setQuerys(request.getQueryParams());
        apiRequest.setGenerateNonce(false);
        request.getHeaders().forEach((headerName, headerValues) -> {
            headerValues.forEach(headerValue -> {
                apiRequest.addHeader(headerName, headerValue);
            });
        });
        apiRequest.addHeader(SDKConstant.X_CA_TIMESTAMP, timestamp);
        apiRequest.addHeader(SDKConstant.X_CA_NONCE, nonce);

        // 为请求添加请求头（同时重新生成签名）
        ApiRequestMaker.make(apiRequest, appKey, appSecret);

        return apiRequest.getHeaders().get(SDKConstant.X_CA_SIGNATURE.toLowerCase()).get(0);
    }

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, User user, Api api) {
        try {
            // 获取原始响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory dataBufferFactory = originalResponse.bufferFactory();

            // 响应状态码为 200
            if (originalResponse.getStatusCode() == HttpStatus.OK) {
                ServerHttpResponseDecorator serverHttpResponseDecorator = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        // 调用转发的接口后执行
                        if (body instanceof Flux) {
                            // 拼接字符串
                            return super.writeWith(
                                    Flux.from(body).map(dataBuffer -> {
                                        // 7、调用成功 接口调用次数 + 1
                                        innerUserApiInvokeService.invoke(api.getId(), user, 2L);
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer); // 释放掉内存
                                        // 构建日志
                                        String data = new String(content, StandardCharsets.UTF_8);
                                        // 打印日志
                                        log.info("标识 {}, 响应结果: {}", exchange.getRequest().getId(), data);
                                        return dataBufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8、调用失败，返回一个规范的错误码
                            log.error("标识 {}, 响应状态码 {}", exchange.getRequest().getId(), getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(serverHttpResponseDecorator).build());
            }
            // 降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        // 必须是小于 -1, 否则不会执行 writeWith
        return -2;
    }
}
