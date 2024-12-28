package com.w83ll43.alliance.common.service;

import com.w83ll43.alliance.common.model.entity.User;

public interface InnerUserApiInvokeService {

    /**
     * 执行接口调用
     * @param aid    接口 ID
     * @param user   用户
     * @param reduce 扣除余额
     * @return 是否成功
     */
    boolean invoke(Long aid, User user, Long reduce);
}
