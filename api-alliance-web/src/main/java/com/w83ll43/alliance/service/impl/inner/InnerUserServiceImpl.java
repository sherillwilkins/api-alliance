package com.w83ll43.alliance.service.impl.inner;

import com.w83ll43.alliance.common.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Override
    public String get(String name) {
        return "Dubbo :" + name;
    }
}
