package com.w83ll43.alliance.sdk.client;

import com.w83ll43.alliance.sdk.enums.Scheme;
import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import com.w83ll43.alliance.sdk.model.response.ApiResponse;

/**
 * apiClient基类
 */
public abstract class BaseApiClient {

    /**
     * appKey
     */
    String appKey;

    /**
     * secretKey
     */
    String appSecret;

    /**
     * 请求协议
     */
    Scheme scheme;

    /**
     * 请求地址
     */
    String host;

    /**
     * 发送同步请求
     * @param apiRequest
     * @return
     */
    protected abstract ApiResponse sendSyncRequest(ApiRequest apiRequest);
}
