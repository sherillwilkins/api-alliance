package com.w83ll43.alliance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w83ll43.alliance.common.model.entity.Api;
import com.w83ll43.alliance.mapper.ApiMapper;
import com.w83ll43.alliance.model.dto.ApiAddDto;
import com.w83ll43.alliance.service.ApiService;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

    @Override
    public String addApi(ApiAddDto apiAddDto) {
        return null;
    }
}




