package com.playtika.sentiment.demo.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topic;

    public void sendPost(String post) {
        log.info("Sending user {} to topic {}", post, topic);
        kafkaTemplate.send(topic, post);
    }
}
