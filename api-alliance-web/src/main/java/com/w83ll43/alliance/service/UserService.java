package com.w83ll43.alliance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.w83ll43.alliance.common.model.entity.User;

public interface UserService extends IService<User> {

    /**
     * 扣减钱包积分
     * @param uid 用户 ID
     * @param reduce 扣减数
     */
    void reduceWalletBalance(Long uid, Long reduce);
}
