package com.w83ll43.alliance.common.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = 5400;
    }

    public Integer getCode() {
        return code;
    }
}