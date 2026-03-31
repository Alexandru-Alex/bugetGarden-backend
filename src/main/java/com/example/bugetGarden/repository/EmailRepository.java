package com.example.bugetGarden.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class EmailRepository {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public void save(String email) {
        String sql = "INSERT INTO emails (email, created_at) VALUES (?, now())";
        jdbcTemplate.update(sql, email);
    }
}
