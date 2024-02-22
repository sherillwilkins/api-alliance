package com.w83ll43.alliance.sdk.exception;

/**
 * SDK 客户端异常
 */
public class SDKException extends RuntimeException {

    public SDKException(String message) {
        super(message);
    }

    public SDKException(String message, Throwable cause) {
        super(message, cause);
    }

    public SDKException(Throwable cause) {
        super(cause);
    }
}
