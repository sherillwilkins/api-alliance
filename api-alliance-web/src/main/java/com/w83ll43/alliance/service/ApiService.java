package com.w83ll43.alliance.service;

import com.w83ll43.alliance.common.model.entity.Api;
import com.baomidou.mybatisplus.extension.service.IService;
import com.w83ll43.alliance.model.dto.ApiAddDto;

public interface ApiService extends IService<Api> {


    String addApi(ApiAddDto apiAddDto);
}
