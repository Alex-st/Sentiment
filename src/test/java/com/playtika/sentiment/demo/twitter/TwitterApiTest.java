package com.playtika.sentiment.demo.twitter;

import com.playtika.sentiment.demo.properties.TwitterProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class TwitterApiTest {

    private TwitterProperties twitterProperties = new TwitterProperties();

    private TwitterApi twitterApi = new TwitterApi(twitterProperties);

    @Test
    public void test() throws Exception {
        List<String> result = twitterApi.getTwits();
        System.out.println(result);
    }
}