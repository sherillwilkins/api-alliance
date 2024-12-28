package com.w83ll43.alliance.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 */
@Data
@TableName(value = "user")
public class User implements Serializable {
    /**
     * 用户 ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 调用接口凭证 AccessKey
     */
    @TableField(value = "access_key")
    private String accessKey;

    /**
     * 调用接口凭证 SecretKey
     */
    @TableField(value = "secret_key")
    private String secretKey;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户简介
     */
    @TableField(value = "profile")
    private String profile;

    /**
     * 钱包余额
     */
    @TableField(value = "balance")
    private Long balance;

    /**
     * 用户状态 0-正常 1-封号
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}