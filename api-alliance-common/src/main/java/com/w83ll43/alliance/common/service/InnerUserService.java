package com.w83ll43.alliance.common.service;

import com.w83ll43.alliance.common.model.entity.User;

public interface InnerUserService {

    /**
     * 根据 AccessKey 获取用户信息
     * @param accessKey 接口调用凭证 AccessKey
     * @return 用户信息
     */
    User getUserByAccessKey(String accessKey);
}
