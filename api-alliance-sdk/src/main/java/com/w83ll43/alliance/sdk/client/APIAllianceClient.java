package com.w83ll43.alliance.sdk.client;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.w83ll43.alliance.sdk.constant.SDKConstant;
import com.w83ll43.alliance.sdk.enums.HttpMethod;
import com.w83ll43.alliance.sdk.enums.ParamPosition;
import com.w83ll43.alliance.sdk.model.entity.Joke;
import com.w83ll43.alliance.sdk.model.entity.Sentence;
import com.w83ll43.alliance.sdk.model.request.ApiRequest;
import com.w83ll43.alliance.sdk.model.response.ApiResponse;

public class APIAllianceClient extends ApacheHttpClient {

    /**
     * 请求地址
     */
    private static final String GATEWAY_HOST = "localhost:10086";

    public APIAllianceClient(String appKey, String appSecret) {
        super.init(appKey, appSecret, GATEWAY_HOST);
    }

    /**
     * 获取随机笑话
     * @return 随机笑话
     */
    public ApiResponse getRandomJoke() {
        String path = "/api/joke";
        ApiRequest apiRequest = new ApiRequest(HttpMethod.GET, path);
        return sendSyncRequest(apiRequest);
    }

    /**
     * 获取随机句子
     * @return 随机句子
     */
    public ApiResponse getRandomSentence() {
        String path = "/api/sentence";
        ApiRequest apiRequest = new ApiRequest(HttpMethod.GET, path);
        return sendSyncRequest(apiRequest);
    }

    /**
     * 根据类型获取随机句子 GET 请求
     * @param type 句子类型
     * @return 随机句子
     */
    public ApiResponse getRandomSentence(String type) {
        String path = "/api/sentence";
        ApiRequest apiRequest = new ApiRequest(HttpMethod.GET, path);
        apiRequest.addParam("type", type, ParamPosition.QUERY, true);
        return sendSyncRequest(apiRequest);
    }

    private <T> T parseResponseToEntity(ApiResponse apiResponse, Class<T> clazz) {
        JSONObject jsonObject = JSONUtil.parseObj(apiResponse.getStringBody());
        return JSONUtil.toBean(JSONUtil.toJsonStr(jsonObject.get("data")), clazz);
    }

    /**
     * 将 ApiResponse 对象解析为 String
     * @param response 响应对象
     * @return 响应字符串
     */
    public static String getResultString(ApiResponse response) {
        StringBuilder result = new StringBuilder();
        result.append("Response from backend server").append(SDKConstant.LF).append(SDKConstant.LF);
        result.append("ResultCode:").append(SDKConstant.LF).append(response.getCode()).append(SDKConstant.LF).append(SDKConstant.LF);
        if (response.getCode() != 200) {
            result.append("Error description:").append(response.getHeaders().get(SDKConstant.X_CA_ERROR_MESSAGE)).append(SDKConstant.LF).append(SDKConstant.LF);
        }
        result.append("ResultBody:").append(SDKConstant.LF).append(new String(response.getBytesBody(), SDKConstant.ENCODING));
        return result.toString();
    }
}
