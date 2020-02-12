package com.playtika.sentiment.demo.vertica;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VerticaDao {

    private static final String QUERY = "select text, likes_count from sm_fact_facebook_supergroup_posts p\n" +
            "order by post_created_ts desc limit 500;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Pair<String, Integer>> getPosts() {
        log.info("Starting fetch vertica posts");
        List<Pair<String, Integer>> posts = jdbcTemplate.query(QUERY, new VerticaPostsDataMapper());
        log.info("Posts from vertica were extracted: {}", posts);
        return posts;
    }

}
