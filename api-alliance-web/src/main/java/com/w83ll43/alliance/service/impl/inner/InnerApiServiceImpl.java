package com.w83ll43.alliance.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.w83ll43.alliance.common.model.entity.Api;
import com.w83ll43.alliance.common.service.InnerApiService;
import com.w83ll43.alliance.service.ApiService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerApiServiceImpl implements InnerApiService {

    @Resource
    private ApiService apiService;

    /**
     * 获取接口信息
     * @param path   接口地址
     * @param method 接口请求方式
     * @return 接口详情信息
     */
    @Override
    public Api getApiByPathAndMethod(String path, String method) {
        // TODO Method 转换为 Integer
        LambdaQueryWrapper<Api> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Api::getUrl, path);
        lambdaQueryWrapper.eq(Api::getMethod, 1L);
        return apiService.getOne(lambdaQueryWrapper);
    }
}
