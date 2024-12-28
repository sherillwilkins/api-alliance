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
    void testRandomJoke() {
        ApiResponse apiResponse = allianceClient.getRandomJoke();
        System.out.println("joke = " + apiResponse.getData());
    }

    @Test
    void testRandomSentence() {
        ApiResponse apiResponse = allianceClient.getRandomSentence("a");
        System.out.println("sentence = " + apiResponse.getData());
    }

    @Test
    void testRequestRateLimiter() {
        for (int i = 0; i < 100; i++) {
            ApiResponse apiResponse = allianceClient.getRandomJoke();
            if (apiResponse.getEx() != null) break;
            System.out.println("joke " + i  + " = " + apiResponse);
        }
    }
}
