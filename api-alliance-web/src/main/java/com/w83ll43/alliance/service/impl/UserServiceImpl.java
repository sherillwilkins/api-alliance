package com.w83ll43.alliance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.mapper.UserMapper;
import com.w83ll43.alliance.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




