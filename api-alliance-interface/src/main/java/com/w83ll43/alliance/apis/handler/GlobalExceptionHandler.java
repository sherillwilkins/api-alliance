package com.w83ll43.alliance.apis.handler;

import com.w83ll43.alliance.common.exception.BusinessException;
import com.w83ll43.alliance.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     * @param e 异常
     * @return 失败结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public Result<String> exceptionHandler(BusinessException e) {
        log.error(e.getMessage());
        return Result.error(5400, e.getMessage());
    }

    /**
     * 处理系统内部异常
     * @param e 异常
     * @return 失败结果
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(5500, e.getMessage());
    }

}
