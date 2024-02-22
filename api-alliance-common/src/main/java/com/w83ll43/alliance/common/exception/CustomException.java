package com.w83ll43.alliance.common.exception;

/**
 * 自定义业务异常
 */
public class CustomException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
        this.code = 5400;
    }

    public Integer getCode() {
        return code;
    }
}