package com.w83ll43.alliance.sdk.enums;

/**
 * 请求协议
 */
public enum Scheme {

    HTTP("HTTP"),
    HTTPS("HTTPS"),
    WEBSOCKET("WS");

    private final String value;

    Scheme(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
