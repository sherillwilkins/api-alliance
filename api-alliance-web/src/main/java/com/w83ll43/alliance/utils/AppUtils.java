package com.w83ll43.alliance.utils;

import com.w83ll43.alliance.common.exception.CustomException;
import com.w83ll43.alliance.common.model.enums.ErrorEnum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class AppUtils {

    /**
     * 关键词
     */
    private final static String SERVER_NAME = "OpenAPI_w83ll43";

    /**
     * 字符
     */
    private final static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    /**
     * 短8位UUID思想其实借鉴微博短域名的生成方式 但是其重复概率过高 而且每次生成4个 需要随即选取一个
     * 本算法利用62个可打印字符 通过随机生成32位UUID 由于UUID都为十六进制 所以将UUID分成8组 每4个为一组
     * 然后通过模62操作 结果作为索引取出字符 这样重复率大大降低
     * 经测试 在生成一千万个数据也没有出现重复 完全满足大部分需求
     * @return appId
     */
    public static String getAppId() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }


    /**
     * 通过 appId 和内置关键词生成 appSecret
     * @param appId appId
     * @return appSecret
     */
    public static String getAppSecret(String appId) {
        try {
            String[] array = new String[]{appId, SERVER_NAME};
            StringBuilder stringBuilder = new StringBuilder();
            // 字符串排序
            Arrays.sort(array);
            for (String s : array) {
                stringBuilder.append(s);
            }
            String str = stringBuilder.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hex = new StringBuilder();
            String shaHex = "";
            for (byte b : digest) {
                shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hex.append(0);
                }
                hex.append(shaHex);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorEnum.SYSTEM_ERROR.getCode(), ErrorEnum.SYSTEM_ERROR.getMessage());
        }
    }
}
