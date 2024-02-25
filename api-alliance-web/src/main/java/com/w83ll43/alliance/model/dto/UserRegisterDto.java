package com.w83ll43.alliance.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@NotEmpty
public class UserRegisterDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 验证码
     */
    @NotEmpty
    private String code;
}
