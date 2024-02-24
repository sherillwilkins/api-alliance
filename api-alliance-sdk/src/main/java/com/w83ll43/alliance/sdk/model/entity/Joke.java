package com.w83ll43.alliance.sdk.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Joke implements Serializable {

    private static final long serialVersionUID = -1988324382962942582L;

    /**
     * 文本ID
     */
    private Integer tid;

    /**
     * 文本
     */
    private String text;
}