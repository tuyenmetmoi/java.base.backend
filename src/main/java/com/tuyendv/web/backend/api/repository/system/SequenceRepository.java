package com.tuyendv.web.backend.api.repository.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getSequence(String prefix, String tableName) {
        String sql = "SELECT FN_GET_SEQ(?, ?) FROM DUAL";
        return jdbcTemplate.queryForObject(sql, new Object[] {prefix, tableName}, String.class);
    }

}
