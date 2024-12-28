package com.w83ll43.alliance.common.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息表
 */
@Data
@TableName(value = "api")
public class Api implements Serializable {
    /**
     * 接口 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口图片地址
     */
    @TableField(value = "image")
    private String image;

    /**
     * 接口名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 接口地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求方式 1-GET 2-POST
     */
    @TableField(value = "method")
    private Integer method;

    /**
     * 接口描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 请求参数
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 响应参数
     */
    @TableField(value = "response_params")
    private String responseParams;

    /**
     * 请求头
     */
    @TableField(value = "request_header")
    private String requestHeader;

    /**
     * 响应头
     */
    @TableField(value = "response_header")
    private String responseHeader;

    /**
     * 返回格式 1-JSON
     */
    @TableField(value = "return_type")
    private Integer returnType;

    /**
     * 接口状态 0-下线 1-上线
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
     * 是否删除 0-否 1-是
     */
    @TableField(value = "is_delete", select = false)
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}