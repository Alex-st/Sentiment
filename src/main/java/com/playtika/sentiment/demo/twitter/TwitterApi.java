package com.playtika.sentiment.demo.twitter;


import com.playtika.sentiment.demo.properties.TwitterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class TwitterApi {

    private TwitterProperties twitterProperties;

    public List<String> getTwits() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterProperties.getConsumerKey())
                .setOAuthConsumerSecret(twitterProperties.getConsumerSecret())
                .setOAuthAccessToken(twitterProperties.getAccessToken())
                .setOAuthAccessTokenSecret(twitterProperties.getAcessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query("slotomania");
        query.setCount(200);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            log.error("Twitter posts were not extracted", e);
        }
        log.info("{} tweets was fetched", result.getTweets().size());
        List<String> tweets = result.getTweets().stream().map(Status::getText).collect(Collectors.toList());
        String tweetsString = String.join(";", tweets);
        log.info("Tweets: {}", tweetsString);
        return tweets;
    }
}
