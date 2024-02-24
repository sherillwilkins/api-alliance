package com.w83ll43.alliance.common.utils;

/**
 * 基于 ThreadLocal 封装工具类
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置 ID
     * @param id id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取 ID
     * @return ID
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
