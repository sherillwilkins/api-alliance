package com.w83ll43.alliance.sdk.utils;

import com.w83ll43.alliance.sdk.constant.HttpConstant;
import com.w83ll43.alliance.sdk.constant.SDKConstant;
import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ApiRequest 构建器
 */
public class ApiRequestMaker {

    public static void make(ApiRequest request , String appKey , String appSecret){

        /**
         * 将 pathParams 中的 value 替换掉 path 中的动态参数
         * 比如 path=/v2/getUserInfo/[userId]，pathParams 字典中包含 key:userId , value:10000003
         * 替换后 path会变成/v2/getUserInfo/10000003
         */
        request.setPath(combinePathParam(request.getPath() , request.getPathParams()));

        /**
         *  拼接URL
         *  HTTP + HOST + PATH(With PathParameter) + Query Parameter
         */
        StringBuilder url = new StringBuilder().append(request.getHost()).append(request.getPath());

        if(null != request.getQuerys() && !request.getQuerys().isEmpty()){
            url.append("?").append(HttpCommonUtil.buildParamString(request.getQuerys()));
        }

        request.setUrl(url.toString());

        Date current = request.getCurrentDate() == null ? new Date() : request.getCurrentDate();
        // 设置请求头中的时间戳
        if(null == request.getFirstHeaderValue(HttpConstant.HTTP_HEADER_DATE)) {
            request.addHeader(HttpConstant.HTTP_HEADER_DATE, getHttpDateHeaderValue(current));
        }

        // 设置请求头中的时间戳 以 timeIntervalSince1970 的形式
        request.addHeader(SDKConstant.X_CA_TIMESTAMP, String.valueOf(current.getTime()));

        // 请求放重放 Nonce 15 分钟内保持唯一 建议使用UUID
        if(request.isGenerateNonce()){
            if(null == request.getFirstHeaderValue(SDKConstant.X_CA_NONCE)) {
                request.addHeader(SDKConstant.X_CA_NONCE, UUID.randomUUID().toString());
            }
        }

        // 设置请求头中的 UserAgent
        request.addHeader(HttpConstant.HTTP_HEADER_USER_AGENT, SDKConstant.USER_AGENT);

        // 设置请求头中的主机地址
        request.addHeader(HttpConstant.HTTP_HEADER_HOST, request.getHost());

        // 设置请求头中的 Api 绑定的的 AppKey
        if(request.isNeedSignature()) {
            request.addHeader(SDKConstant.X_CA_KEY, appKey);
        }

        // 设置签名版本号
        request.addHeader(SDKConstant.CA_VERSION, SDKConstant.CA_VERSION_VALUE);

        // 设置请求数据类型
        if(null == request.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_TYPE)) {
            request.addHeader(HttpConstant.HTTP_HEADER_CONTENT_TYPE, request.getMethod().getRequestContentType());
        }

        // 设置应答数据类型
        if(null == request.getFirstHeaderValue(HttpConstant.HTTP_HEADER_ACCEPT)){
            request.addHeader(HttpConstant.HTTP_HEADER_ACCEPT, request.getMethod().getAcceptContentType());
        }

        // 设置签名函数方法
        if (request.isNeedSignature() && !StringUtils.isEmpty(request.getSignatureMethod())) {
            request.addHeader(SDKConstant.X_CA_SIGNATURE_METHOD, request.getSignatureMethod());
        }

        /**
         *  如果 formParams 不为空
         *  将 Form 中的内容拼接成字符串后使用 UTF8 编码序列化成 Byte 数组后加入到 Request 中去
         */
        if(null != request.getBytesBody() && request.isGenerateContentMd5() && request.getBytesBody().length >0 && null == request.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_MD5)){
            request.addHeader(HttpConstant.HTTP_HEADER_CONTENT_MD5 , SignUtil.messageDigest(request.getBytesBody()));
        }

        /**
         *  将 Request 中的 httpMethod、headers、path、queryParam、formParam 合成一个字符串
         *  用 hmacSha256/hmacSha1 算法双向加密进行签名
         *  签名内容放到 Http 头中 用作服务器校验
         */
        if(request.isNeedSignature()) {
            String signature = SignUtil.sign(request, appSecret);
            request.addHeader(SDKConstant.X_CA_SIGNATURE, signature);
        }

        /**
         *  凑齐所有 HTTP 头之后 将头中的数据全部放入 Request 对象中
         *  Http 头编码方式: 先将字符串进行 UTF-8 编码 然后使用 Iso-8859-1 解码生成字符串
         */
        for(String key : request.getHeaders().keySet()){
            List<String> values = request.getHeaders().get(key);
            if(null != values && !values.isEmpty()){
                values.replaceAll(s -> new String(s.getBytes(SDKConstant.ENCODING), SDKConstant.HEADER_ENCODING));
            }
            request.getHeaders().put(key , values);
        }
    }

    /**
     * 整合 Path
     * @param path
     * @param pathParams
     * @return
     */
    private static String combinePathParam(String path , Map<String , String> pathParams){
        if(pathParams == null){
            return path;
        }

        for(String key : pathParams.keySet()){
            path = path.replace("["+key+"]" , pathParams.get(key));
        }
        return path;
    }

    /**
     * 获取 HTTP 请求头 Date 的值
     * @param date
     * @return
     */
    private static String getHttpDateHeaderValue(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.CHINA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return dateFormat.format(date);
    }
}
