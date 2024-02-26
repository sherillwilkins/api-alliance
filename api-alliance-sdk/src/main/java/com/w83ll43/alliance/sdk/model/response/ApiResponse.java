package com.w83ll43.alliance.sdk.model.response;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.w83ll43.alliance.sdk.constant.HttpConstant;
import com.w83ll43.alliance.sdk.constant.SDKConstant;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Data
public final class ApiResponse {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    private JSONObject data;

    /**
     * 动态数据
     */
    private Map<String, Object> map = new HashMap<>();


    /**
     * 响应内容类型
     */
    private String contentType;

    /**
     * 响应异常
     */
    private Exception ex;

    /**
     * 响应头
     */
    private Map<String, List<String>> headers = new HashMap<>();

    /**
     * 响应体
     */
    private byte[] bytesBody;

    /**
     * 响应体
     */
    private String stringBody;

    public ApiResponse(int code) {
        this.code = code;
    }

    public ApiResponse(int errorCode, String message, Exception ex) {
        this.code = errorCode;
        this.message = message;
        this.ex = ex;
    }

    /**
     * 获取请求头的第一个值
     * @param name
     * @return
     */
    public String getFirstHeaderValue(String name) {
        if (headers.containsKey(name) && !headers.get(name).isEmpty()) {
            return headers.get(name).get(0);
        }
        return null;
    }

    /**
     * 添加请求头
     * @param name  键
     * @param value 值
     */
    public void addHeader(String name, String value) {
        name = name.trim().toLowerCase();
        addParam(name, value, headers);
    }

    /**
     * 添加请求头
     * @param name
     * @param value
     * @param map
     */
    public void addParam(String name, String value, Map<String, List<String>> map) {
        if (map.containsKey(name)) {
            map.get(name).add(value);
        } else {
            List<String> values = new ArrayList<>();
            values.add(value == null ? "" : value.trim());
            map.put(name, values);
        }
    }

    public ApiResponse(JsonNode jsonObject) throws IOException, JsonParseException, JsonMappingException {
        this.parse(jsonObject);
        if (jsonObject.get("status") != null) {
            this.code = Integer.parseInt(jsonObject.get("status").asText());
        }
        this.contentType = getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_TYPE);
        if (null != this.getFirstHeaderValue(SDKConstant.X_CA_ERROR_MESSAGE)) {
            this.message = this.getFirstHeaderValue(SDKConstant.X_CA_ERROR_MESSAGE);
        }
    }

    public void parse(JsonNode message) {
        JsonNode headers = message.get("header");
        if (headers != null && !headers.isEmpty()) {
            Iterator<String> names = headers.fieldNames();
            while (names.hasNext()) {
                String name = names.next();
                if (headers.get(name) != null) {
                    if (!headers.get(name).isEmpty()) {
                        for (JsonNode value : headers.get(name)) {
                            addHeader(name, value.asText());
                        }
                    } else {
                        addHeader(name, headers.get(name).asText());
                    }
                }
            }
        }

        String contentType = this.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_TYPE);
        Charset charset = SDKConstant.ENCODING;
        if (null != contentType) {
            try {
                contentType = contentType.toLowerCase();
                String[] charsetStr = contentType.split(";");
                for (String s : charsetStr) {
                    if (s.contains("charset")) {
                        charset = Charset.forName(s.substring(s.indexOf("=") + 1));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        JsonNode bodyNode = message.get("body");
        if (bodyNode != null) {
            stringBody = bodyNode.asText();
            bytesBody = stringBody.getBytes(charset);
        }
    }
}