package com.w83ll43.alliance.common.model.entity;

import lombok.Data;

@Data
public class RequestParam {

    /**
     * 请求参数名
     */
    private String key;

    /**
     * 请求参数描述
     */
    private String description;

    /**
     * 请求参数位置
     * 路径、查询、表单
     */
    private String position;
}
