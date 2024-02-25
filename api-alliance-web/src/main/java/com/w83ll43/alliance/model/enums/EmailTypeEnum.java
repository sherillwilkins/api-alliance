package com.w83ll43.alliance.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 邮件类型枚举
 */
@Getter
@AllArgsConstructor
public enum EmailTypeEnum {

    TEST(0, "测试邮件"),
    REGISTER(1, "注册账号"),
    RESET_PASSWORD(2, "重置密码"),
    RESET_EMAIL(3, "重置邮箱");

    private final Integer type;

    private final String desc;

    private static final Map<Integer, EmailTypeEnum> cache;

    static {
        cache = Arrays.stream(EmailTypeEnum.values()).collect(Collectors.toMap(EmailTypeEnum::getType, Function.identity()));
    }

    public static EmailTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
