package com.playtika.sentiment.demo.vertica;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VerticaPostsDataMapper implements RowMapper<Pair<String, Integer>> {
    @Override
    public Pair<String, Integer> mapRow(ResultSet resultSet, int i) throws SQLException {
        return Pair.of(resultSet.getString("text"), resultSet.getInt("likes_count"));
    }
}
