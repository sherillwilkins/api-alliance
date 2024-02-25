package com.w83ll43.alliance.controller;

import com.w83ll43.alliance.common.model.Result;
import com.w83ll43.alliance.common.utils.AppUtils;
import com.w83ll43.alliance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{appid}")
    public Result<String> test(@PathVariable("appid") String appid) {
        return Result.success(AppUtils.getAppSecret(appid));
    }

//    @PostMapping("/register")
//    public Result<String> register(@RequestBody String body) {
//        return userService.register(body);
//    }
}
