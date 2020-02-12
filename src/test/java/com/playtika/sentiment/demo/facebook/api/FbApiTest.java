package com.playtika.sentiment.demo.facebook.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class FbApiTest {

    private FbApi fbApi = new FbApi();

    @Test
    public void test() {

        String response = fbApi.getPostsFromGroup();
        System.out.println(response);
    }

}