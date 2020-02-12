package com.playtika.sentiment.demo.services;

import com.playtika.sentiment.demo.kafka.KafkaProducer;
import com.playtika.sentiment.demo.twitter.TwitterApi;
import com.playtika.sentiment.demo.vertica.VerticaDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FetchPostsService {

    @Autowired
    private VerticaDao verticaDao;

    @Autowired
    private TwitterApi twitterApi;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void fetchPosts() {

        List<String> tweeterPosts = twitterApi.getTwits();
        List<String> verticaPosts = verticaDao.getPosts().stream().map(Pair::getKey).collect(Collectors.toList());
        log.info("Start sending messages to kafka");

        List<String> union = ListUtils.union(verticaPosts, tweeterPosts);
        union.forEach(post -> kafkaProducer.sendPost(post));
    }
}
