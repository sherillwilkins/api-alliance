package com.w83ll43.alliance;

import com.w83ll43.alliance.sdk.client.APIAllianceClient;
import com.w83ll43.alliance.sdk.model.entity.Joke;
import com.w83ll43.alliance.sdk.model.entity.Sentence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SDKTests {

    @Resource
    private APIAllianceClient allianceClient;

    @Test
    void testRandomJoke() {
        Joke joke = allianceClient.getRandomJoke();
        System.out.println("joke = " + joke);
    }

    @Test
    void testRandomSentence() {
        Sentence sentence = allianceClient.getRandomSentence("a");
        System.out.println("sentence = " + sentence);
    }
}
