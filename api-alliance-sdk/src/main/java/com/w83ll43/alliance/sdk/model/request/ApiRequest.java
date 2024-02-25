package com.w83ll43.alliance.sdk.model.request;

import com.w83ll43.alliance.sdk.signature.HMacSHA256SignerFactory;
import com.w83ll43.alliance.sdk.enums.HttpMethod;
import com.w83ll43.alliance.sdk.enums.ParamPosition;
import com.w83ll43.alliance.sdk.enums.Scheme;
import com.w83ll43.alliance.sdk.exception.SDKException;

import java.util.*;

public final class ApiRequest {

    /**
     * 请求协议
     */
    private Scheme scheme;

    /**
     * 请求方法枚举
     */
    private HttpMethod method;

    /**
     * host 地址
     */
    private String host;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求 URL
     */
    private String url;

    /**
     * 字符串类型请求体
     */
    private String stringBody;

    /**
     * 字节数组类型请求体
     */
    private byte[] bytesBody;

    /**
     * 请求头
     */
    private Map<String, List<String>> headers = new HashMap<>();

    /**
     * 签名方法
     */
    private String signatureMethod = HMacSHA256SignerFactory.METHOD;

    /**
     * 当前时间
     */
    private Date currentDate;

    /**
     * 内容是否进行 MD5 加密
     */
    private boolean isGenerateContentMd5 = true;

    /**
     * 是否生成随机数
     */
    private boolean isGenerateNonce = true;

    /**
     * json 参数
     */
    private Map<String, Object> jsonParams = new HashMap<>();

    /**
     * 路径参数
     */
    private Map<String, String> pathParams = new HashMap<>();

    /**
     * 查询参数
     */
    private Map<String, List<String>> querys = new HashMap<>();

    /**
     * 表单参数
     */
    private Map<String, List<String>> formParams = new HashMap<>();

    /**
     * 是否需要签名
     */
    private boolean isNeedSignature = true;

    public ApiRequest(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
        this.bytesBody = new byte[]{};
    }

    public ApiRequest(HttpMethod method, String path, byte[] bytesBody) {
        this.method = method;
        this.path = path;
        this.bytesBody = bytesBody;
    }

    /**
     * 添加请求头
     * @param name 键
     * @param value 值
     */
    public void addHeader(String name, String value){
        // 去除不可见符号并转为小写
        name = name.trim().toLowerCase();
        addParam(name, value, headers);
    }

    /**
     * 添加请求头
     * @param name
     * @param value
     * @param map
     */
    public void addParam(String name, String value, Map<String, List<String>> map){
        // 键已经存在
        if(map.containsKey(name)){
            map.get(name).add(value);
        } else {
            List<String> values = new ArrayList<>();
            values.add(value == null ? "" : value.trim());
            map.put(name, values);
        }
    }

    /**
     * 添加参数
     * @param name
     * @param value
     * @param position
     * @param isRequired
     */
    public void addParam(String name, String value, ParamPosition position, boolean isRequired) {
        // 判断是否必须
        if (value == null) {
            if (isRequired) {
                throw new SDKException(String.format("参数 %s 的值不可为空", name));
            } else {
                return;
            }
        }

        switch (position) {
            case HEAD: {
                // 添加到请求头
                addHeader(name, value);
                return;
            }
            case PATH: {
                // 添加到路径
                this.pathParams.put(name, value);
                break;
            }
            case QUERY: {
                addParam(name, value, querys);
                break;
            }
            case BODY: {
                addParam(name, value, formParams);
                break;
            }
            default: {
                throw new SDKException("未知参数位置: " + position);
            }
        }
    }


    /**
     * 添加数据类型参数
     * @param name
     * @param value
     * @param position
     * @param isRequired
     */
    public void addParam(String name, List<String> value, ParamPosition position, boolean isRequired) {
        // 判断是否必须
        if (value == null) {
            if (isRequired) {
                throw new SDKException(String.format("参数 %s 的值不可为空", name));
            } else {
                return;
            }
        }

        if(position == ParamPosition.PATH){
            throw new SDKException(String.format("路径参数 %s 不支持数组类型", name));
        }

        for(String str : value){
            addParam(name, str, position, isRequired);
        }

    }

    /**
     * 获取请求头的第一个值
     * @param name
     * @return
     */
    public String getFirstHeaderValue(String name){
        if(headers.containsKey(name) && !headers.get(name).isEmpty()){
            return headers.get(name).get(0);
        }
        return null;
    }

    /**
     * 复制 API 请求
     * @return
     */
    public ApiRequest duplicate(){
        ApiRequest apiRequest = new ApiRequest(method , path , bytesBody);
        if(null != host) {
            apiRequest.host = host;
        }
        if(null != url) {
            apiRequest.url = url;
        }
        apiRequest.pathParams = new HashMap<>();
        apiRequest.pathParams.putAll(pathParams);

        apiRequest.headers = new HashMap<>();
        apiRequest.headers.putAll(headers);

        apiRequest.querys = new HashMap<>();
        apiRequest.querys.putAll(querys);

        apiRequest.formParams = new HashMap<>();
        apiRequest.formParams.putAll(formParams);

        if(null != signatureMethod) {
            apiRequest.signatureMethod = signatureMethod;
        }
        return apiRequest;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public String getStringBody() {
        return stringBody;
    }

    public void setStringBody(String stringBody) {
        this.stringBody = stringBody;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBytesBody() {
        return bytesBody;
    }

    public void setBytesBody(byte[] bytesBody) {
        this.bytesBody = bytesBody;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isGenerateContentMd5() {
        return isGenerateContentMd5;
    }

    public void setGenerateContentMd5(boolean generateContentMd5) {
        isGenerateContentMd5 = generateContentMd5;
    }

    public boolean isGenerateNonce() {
        return isGenerateNonce;
    }

    public void setGenerateNonce(boolean generateNonce) {
        isGenerateNonce = generateNonce;
    }

    public Map<String, Object> getJsonParams() {
        return jsonParams;
    }

    public void setJsonParams(Map<String, Object> jsonParams) {
        this.jsonParams = jsonParams;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams = pathParams;
    }

    public Map<String, List<String>> getQuerys() {
        return querys;
    }

    public void setQuerys(Map<String, List<String>> querys) {
        this.querys = querys;
    }

    public Map<String, List<String>> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, List<String>> formParams) {
        this.formParams = formParams;
    }

    public boolean isNeedSignature() {
        return isNeedSignature;
    }

    public void setNeedSignature(boolean needSignature) {
        isNeedSignature = needSignature;
    }
}
