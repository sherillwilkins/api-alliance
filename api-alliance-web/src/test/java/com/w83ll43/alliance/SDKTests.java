package com.w83ll43.alliance;

import com.w83ll43.alliance.sdk.client.APIAllianceClient;
import com.w83ll43.alliance.sdk.model.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SDKTests {

    @Resource
    private APIAllianceClient allianceClient;

    @Test
    void test() {
        ApiResponse joke = allianceClient.getRandomJoke();
        System.out.println("joke = " + joke);
    }
}
