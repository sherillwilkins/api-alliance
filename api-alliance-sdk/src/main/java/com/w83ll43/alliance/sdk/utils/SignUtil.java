package com.w83ll43.alliance.sdk.utils;

import com.w83ll43.alliance.sdk.signature.ISignerFactory;
import com.w83ll43.alliance.sdk.constant.HttpConstant;
import com.w83ll43.alliance.sdk.constant.SDKConstant;
import com.w83ll43.alliance.sdk.exception.SDKException;
import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import com.w83ll43.alliance.sdk.model.response.ApiResponse;
import com.w83ll43.alliance.sdk.signature.ISigner;
import com.w83ll43.alliance.sdk.signature.SignerFactoryManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具类
 */
public class SignUtil {

    /**
     * 签名方法 将 HttpMethod、Headers、Path、QueryParam、FormParam 合成一个字符串并用 HmacSHA1/HmacSHA256 算法双向加密进行签名
     * @param request
     * @param secret
     * @return
     */
    public static String sign(ApiRequest request, String secret) {
        try {
            String signString = buildStringToSign(request);
            ISignerFactory signerFactory = SignerFactoryManager.findSignerFactory(request.getSignatureMethod());

            if (null == signerFactory) {
                throw new SDKException("不支持的签名方法: " + request.getSignatureMethod());
            }

            // 获取签名工具
            ISigner signer = signerFactory.getSigner();

            if (null == signer) {
                throw new SDKException("Oops!");
            }

            try {
                return signer.sign(signString, secret);
            } catch (Exception e) {
                throw new SDKException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 Request 中的 HttpMethod、Headers、Path、QueryParam、FormParam 合成一个字符串
     * @param apiRequest
     * @return
     */
    public static String buildStringToSign(ApiRequest apiRequest) {

        StringBuilder stringBuilder = new StringBuilder();

        // HttpMethod
        stringBuilder.append(apiRequest.getMethod().getValue()).append(SDKConstant.LF);

        // 如果有@"Accept"头 这个头需要参与签名
        if (apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_ACCEPT) != null) {
            stringBuilder.append(apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_ACCEPT));
        }
        stringBuilder.append(SDKConstant.LF);

        // 如果有@"Content-MD5"头 这个头需要参与签名
        if (apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_MD5) != null) {
            stringBuilder.append(apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_MD5));
        }
        stringBuilder.append(SDKConstant.LF);

        // 如果有@"Content-Type"头 这个头需要参与签名
        if (apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_TYPE) != null) {
            stringBuilder.append(apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_CONTENT_TYPE));
        }
        stringBuilder.append(SDKConstant.LF);

        // 如果有@"Data"头 这个头需要参与签名
        if (apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_DATE) != null) {
            stringBuilder.append(apiRequest.getFirstHeaderValue(HttpConstant.HTTP_HEADER_DATE));
        }
        stringBuilder.append(SDKConstant.LF);

        // 将 Headers 合成一个字符串
        stringBuilder.append(buildHeaders(apiRequest));

        // 将 Path、QueryParam、FormParam 合成一个字符串
        stringBuilder.append(buildResource(apiRequest));

        return stringBuilder.toString();
    }

    /**
     * 将 Response 中的 HttpMethod、Headers、Path、QueryParam、FormParam 合成一个字符串
     * @param apiResponse
     * @return
     */
    public static String buildStringToSign(ApiResponse apiResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiResponse.getCode()).append(SDKConstant.LF);
        String signatureHeaders = apiResponse.getFirstHeaderValue(SDKConstant.X_CA_SIGNATURE_HEADERS);

        if (!StringUtils.isBlank(signatureHeaders)) {
            signatureHeaders = signatureHeaders.toLowerCase();
            String[] signatureHeaderList = signatureHeaders.split(",");

            for (String s : signatureHeaderList) {
                if (apiResponse.getFirstHeaderValue(s) != null) {
                    stringBuilder.append(apiResponse.getFirstHeaderValue(s));
                    stringBuilder.append(SDKConstant.LF);
                }
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将 Path、QueryParam、FormParam 合成一个字符串
     * 构造后的字符串为 ?key1=value1&key2=value2
     */
    private static String buildResource(ApiRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        // 添加 Path
        stringBuilder.append(request.getPath());

        // 使用 TreeMap 排序 默认按照字母排序
        TreeMap<String, String> parameter = new TreeMap<>();

        // 添加 QueryParam
        if (null != request.getQuerys() && !request.getQuerys().isEmpty()) {
            for (Map.Entry<String, List<String>> entry : request.getQuerys().entrySet()) {
                if (entry.getValue() != null) {
                    parameter.put(entry.getKey(), entry.getValue().get(0));
                }
            }
        }

        // 添加 FormParam
        if (null != request.getFormParams() && !request.getFormParams().isEmpty()) {
            for (Map.Entry<String, List<String>> entry : request.getFormParams().entrySet()) {
                if (entry.getValue() != null) {
                    parameter.put(entry.getKey(), entry.getValue().get(0));
                }
            }
        }

        // 字符串
        if (!parameter.isEmpty()) {
            stringBuilder.append("?");
            boolean isFirst = true;
            for (String key : parameter.keySet()) {
                if (!isFirst) {
                    stringBuilder.append("&");
                } else {
                    isFirst = false;
                }
                stringBuilder.append(key);
                String value = parameter.get(key);
                if (null != value && !value.isEmpty()) {
                    stringBuilder.append("=").append(value);
                }
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将 Headers 合成一个字符串
     * 需要注意的是 HTTP 头需要按照字母排序加入签名字符串
     * 同时所有加入签名的头的列表 需要用逗号分隔形成一个字符串 加入一个新 HTTP 头@"X-Ca-Signature-Headers"
     */
    private static String buildHeaders(ApiRequest apiRequest) {
        // TODO 自定义参与签名的请求头

        // 使用 TreeMap 排序 默认按照字母排序
        Map<String, String> headersToSign = new TreeMap<>();

        // 参与签名的请求头
        StringBuilder signHeadersStringBuilder = new StringBuilder();

        int flag = 0;
        for (Map.Entry<String, List<String>> header : apiRequest.getHeaders().entrySet()) {
            // 如果请求头包含 "X-Ca" 则参与签名
            if (header.getKey().startsWith(SDKConstant.CA_HEADER_TO_SIGN_PREFIX_SYSTEM.toLowerCase())) {
                if (flag != 0) {
                    signHeadersStringBuilder.append(",");
                }
                flag++;
                signHeadersStringBuilder.append(header.getKey());
                headersToSign.put(header.getKey(), apiRequest.getFirstHeaderValue(header.getKey()));
            }
        }

        // 同时所有加入签名的头的列表 需要用逗号分隔形成一个字符串 加入一个新HTTP头@"X-Ca-Signature-Headers"
        apiRequest.addHeader(SDKConstant.X_CA_SIGNATURE_HEADERS, signHeadersStringBuilder.toString());

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> e : headersToSign.entrySet()) {
            stringBuilder.append(e.getKey()).append(':').append(e.getValue()).append(SDKConstant.LF);
        }
        return stringBuilder.toString();
    }

    /**
     * 消息摘要
     * 先进行 MD5 摘要再进行 Base64 编码获取摘要字符串
     * @param bytes
     * @return
     */
    public static String messageDigest(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("消息摘要 参数不能为空");
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            return Base64.encodeBase64String(md.digest());
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("未知算法 MD5");
        }
    }
}
