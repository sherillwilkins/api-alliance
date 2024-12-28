package com.w83ll43.alliance.controller;

import com.w83ll43.alliance.common.model.Result;
import com.w83ll43.alliance.model.dto.ApiAddDto;
import com.w83ll43.alliance.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping
    public Result<String> test(ApiAddDto apiAddDto) {
        apiService.addApi(apiAddDto);
        return Result.success("发布接口成功！等待管理员审核！");
    }
}
