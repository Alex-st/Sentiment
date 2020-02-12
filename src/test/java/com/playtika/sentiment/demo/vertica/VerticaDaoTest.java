package com.playtika.sentiment.demo.vertica;


import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@SpringBootTest
class VerticaDaoTest {

    @Autowired
    private VerticaDao verticaDao;

    @Test
    public void test() {
        List<Pair<String, Integer>> posts = verticaDao.getPosts();

        posts.forEach(post -> {
            System.out.println(post.getKey() + "/" + post.getValue());
        });
    }
}