package com.w83ll43.alliance.sdk.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JokeText implements Serializable {

    private static final long serialVersionUID = -1988324382962942582L;

    /**
     * 文本ID
     */
    private Integer textId;

    /**
     * 文本标题ID
     */
    private Integer textTitleId;

    /**
     * 文本
     */
    private String text;
}