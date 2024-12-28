package com.w83ll43.alliance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.mapper.UserMapper;
import com.w83ll43.alliance.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 扣减钱包积分
     * @param uid    用户 ID
     * @param reduce 扣减数
     */
    @Override
    public void reduceWalletBalance(Long uid, Long reduce) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .setSql("balance = balance - " + reduce)
                .update();
    }
}




