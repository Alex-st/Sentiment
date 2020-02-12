package com.playtika.sentiment.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "twitter")
@Data
public class TwitterProperties {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String acessTokenSecret;
}
