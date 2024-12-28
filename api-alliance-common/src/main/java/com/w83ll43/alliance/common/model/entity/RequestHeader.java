package com.w83ll43.alliance.common.model.entity;

import lombok.Data;

@Data
public class RequestHeader {

    /**
     * 请求头
     */
    private String key;

    /**
     * 请求头描述
     */
    private String description;

    /**
     * 是否必须
     */
    private Boolean isRequired;
}
