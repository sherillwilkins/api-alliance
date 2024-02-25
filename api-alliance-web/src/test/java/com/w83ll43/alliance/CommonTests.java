package com.w83ll43.alliance;

import com.w83ll43.alliance.common.utils.AppUtils;
import com.w83ll43.alliance.common.utils.ValidateCodeUtils;
import org.junit.jupiter.api.Test;

public class CommonTests {

    @Test
    void testGenAccessKey() {
        String appId = AppUtils.getAppId();
        System.out.println("appId = " + appId);
    }

    @Test
    void testGenSecretKey() {
        String appId = "GXjoEmsHdEbYGQpSJ70C7w4FpEmD";
        String appSecret = AppUtils.getAppSecret(appId);
        System.out.println("appSecret = " + appSecret);
    }

    @Test
    void testValidateCodeUtils() {
        String code = ValidateCodeUtils.generateValidateCode4String(6);
        System.out.println("code = " + code);
    }
}
