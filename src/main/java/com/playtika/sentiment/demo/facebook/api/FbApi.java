package com.playtika.sentiment.demo.facebook.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class FbApi {

    private static final String TOKEN = "";
    private static final String GROUP = "";
    private static final String URL = "https://graph.facebook.com/%GROUP%/feed?limit=5&access_token=%ACCESS_TOKEN%";


    public String getPostsFromGroup() {
        RestTemplate restTemplate = new RestTemplate();
        String url = URL.replace("%GROUP%", GROUP).replace("%ACCESS_TOKEN%", TOKEN);
        String body = restTemplate.getForEntity(url, String.class).getBody();
        log.info("Response: {}", body);
        return body;
    }
}
