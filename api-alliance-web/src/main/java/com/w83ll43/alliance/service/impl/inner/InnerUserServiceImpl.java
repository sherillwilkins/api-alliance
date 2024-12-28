package com.w83ll43.alliance.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.common.service.InnerUserService;
import com.w83ll43.alliance.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;

    /**
     * 根据 AccessKey 获取用户信息
     * @param accessKey 接口调用凭证 AccessKey
     * @return 用户信息
     */
    @Override
    public User getUserByAccessKey(String accessKey) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccessKey, accessKey);
        return userService.getOne(lambdaQueryWrapper);
    }
}
