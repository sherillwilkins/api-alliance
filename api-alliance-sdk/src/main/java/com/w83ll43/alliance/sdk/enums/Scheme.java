package com.w83ll43.alliance.sdk.enums;

import lombok.Getter;

/**
 * 请求协议
 */
@Getter
public enum Scheme {

    HTTP("HTTP"),
    HTTPS("HTTPS"),
    WEBSOCKET("WS");

    private final String value;

    Scheme(String value) {
        this.value = value;
    }
}
