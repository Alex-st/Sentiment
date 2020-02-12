package com.playtika.sentiment.demo.configuration;

import com.playtika.sentiment.demo.properties.TwitterProperties;
import com.playtika.sentiment.demo.twitter.TwitterApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(TwitterProperties.class)
public class SentimentConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "vertica.datasource")
    public DataSource verticaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource verticaDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(verticaDataSource);
        jdbcTemplate.setQueryTimeout(60000);
        return jdbcTemplate;
    }

    @Bean
    public TwitterApi twitterApi(TwitterProperties twitterProperties) {
        return new TwitterApi(twitterProperties);
    }
}
