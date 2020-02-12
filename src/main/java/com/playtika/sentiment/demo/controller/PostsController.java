package com.playtika.sentiment.demo.controller;

import com.playtika.sentiment.demo.services.FetchPostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("posts")
public class PostsController {

    @Autowired
    private FetchPostsService fetchPostsService;

    @GetMapping
    public ResponseEntity<Void> syncCampaignHistoryWithThirdParties() {
        log.info("Start get posts {}");

        fetchPostsService.fetchPosts();

        return ResponseEntity.ok().build();
    }
}