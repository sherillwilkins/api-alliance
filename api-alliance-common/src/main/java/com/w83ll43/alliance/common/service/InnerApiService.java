package com.w83ll43.alliance.common.service;

import com.w83ll43.alliance.common.model.entity.Api;

public interface InnerApiService {

    /**
     * 获取接口信息
     * @param path   接口地址
     * @param method 接口请求方式
     * @return 接口详情信息
     */
    Api getApiByPathAndMethod(String path, String method);
}
