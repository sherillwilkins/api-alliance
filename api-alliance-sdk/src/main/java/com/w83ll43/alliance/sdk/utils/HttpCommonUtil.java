package com.w83ll43.alliance.sdk.utils;

import com.w83ll43.alliance.sdk.constant.SDKConstant;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpCommonUtil {
    public static String buildParamString(Map<String, List<String>> params) {
        StringBuilder result = new StringBuilder();
        if (null != params && !params.isEmpty()) {
            boolean isFirst = true;
            for (String key : params.keySet()) {
                try {
                    if (params.get(key) != null) {
                        for (int i = 0; i < params.get(key).size(); i++) {
                            if (isFirst) {
                                isFirst = false;
                            } else {
                                result.append("&");
                            }
                            result.append(key).append("=").append(URLEncoder.encode(params.get(key).get(i), SDKConstant.ENCODING.displayName()));
                        }
                    }
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return result.toString();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
}
