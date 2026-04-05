package com.example.bugetGarden.repository.user;


import com.example.bugetGarden.dto.email.UserEmailIdDto;
import com.example.bugetGarden.dto.google.GoogleInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserEmailIdDto getEmail(String email) {
        String sql = "SELECT id, email FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) ->
                new UserEmailIdDto(rs.getString("id"), rs.getString("email"))
        );
    }

    public UUID createUser(GoogleInfoDto info) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO users (id,email, display_name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                id,
                info.getEmail(),
                info.getName()
        );
        String insertAuthSql = "INSERT INTO user_auth (user_id, provider, provider_user_id, email_verified) VALUES (?, 'google', ?, ?)";
        jdbcTemplate.update(insertAuthSql,
                id,
                info.getId(),
                info.isVerifiedEmail()
        );
        return id;
    }
}
