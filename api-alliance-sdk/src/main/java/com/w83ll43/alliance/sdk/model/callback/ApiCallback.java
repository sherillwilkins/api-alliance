package com.w83ll43.alliance.sdk.model.callback;


import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import com.w83ll43.alliance.sdk.model.response.ApiResponse;

public interface ApiCallback {
    /**
     * 请求失败
     * @param request   封装后的请求对象 包含部分http相关信息
     * @param exception 导致失败的异常
     */
    void onFailure(ApiRequest request, Exception exception);

    /**
     * 收到应答
     * @param request  封装后的请求对象 包含部分http相关信息
     * @param response 封装后的应答对象 包含部分http相关信息 可以调用.getBody()获取content
     */
    void onResponse(ApiRequest request, ApiResponse response);
}