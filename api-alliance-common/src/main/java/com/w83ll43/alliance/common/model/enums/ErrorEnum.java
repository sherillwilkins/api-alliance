package com.w83ll43.alliance.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    PARAM_INVALID(5400, "参数校验失败"),
    SYSTEM_ERROR(5500, "系统出小差了，请稍后再试哦~~"),
    ;

    private final Integer code;

    private final String message;
}
