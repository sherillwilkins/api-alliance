package com.w83ll43.alliance.gateway.handler;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

//@Slf4j
//@Component
public class GatewayExceptionHandler extends DefaultErrorWebExceptionHandler {

    @ConditionalOnMissingBean
    @Bean
    public ErrorProperties errorProperties() {
        return new ErrorProperties();
    }

    //    @Autowired
    public GatewayExceptionHandler(ErrorAttributes errorAttributes,
                                   WebProperties.Resources resources,
                                   ErrorProperties errorProperties,
                                   ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
        ServerCodecConfigurer serverCodecConfigurer = applicationContext.getBean(ServerCodecConfigurer.class);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        // 从这里可以拿到异常信息，后续可以根据不同异常进行不同的返回
        Throwable throwable = super.getError(request);
        Map<String, Object> result = JSONUtil.createObj();
        return result;
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        // errorAttributes 相当于自定义返回值对象，这里需要有状态码，以表明请求是否合理
        return (int) errorAttributes.get("code");
    }
}
