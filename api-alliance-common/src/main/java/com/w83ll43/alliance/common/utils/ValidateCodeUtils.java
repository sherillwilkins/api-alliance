package com.w83ll43.alliance.common.utils;

import java.util.Random;

/**
 * 随机生成验证码工具类
 */
public class ValidateCodeUtils {
    /**
     * 随机生成验证码
     * @param length 长度为4位或者6位
     * @return 纯数字验证码 (Integer)
     */
    public static Integer generateValidateCode(int length) {
        int code;
        if (length == 4) {
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if (code < 1000) {
                code = code + 1000;//保证随机数为4位数字
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if (code < 100000) {
                code = code + 100000;//保证随机数为6位数字
            }
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    /**
     * 随机生成验证码
     * @param length 长度为4位或者6位
     * @return 纯数字验证码(String)
     */
    public static String generateValidateCodeString(int length) {
        return generateValidateCode(length).toString();
    }

    /**
     * 随机生成指定长度字符串验证码
     * @param length 长度
     * @return 字符串验证码
     */
    public static String generateValidateCode4String(int length) {
        Random random = new Random();
        String hash = Integer.toHexString(random.nextInt());
        return hash.substring(0, length);
    }
}
