package com.example.bugetGarden.service.user;

import com.example.bugetGarden.controller.GoogleToken;
import com.example.bugetGarden.dto.email.UserEmailIdDto;
import com.example.bugetGarden.dto.google.GoogleInfoDto;
import com.example.bugetGarden.repository.token.AuthTokenRepository;
import com.example.bugetGarden.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthTokenRepository tokenRepository;

    public String registerGoogleUserIfNecessary(GoogleInfoDto info) {
        UserEmailIdDto userDto;

        try {
            userDto = userRepository.getEmail(info.getEmail());
        } catch (EmptyResultDataAccessException e) {
            var userId = userRepository.createUser(info);
            return tokenRepository.generateToken(userId, "email_verify", 43200);
        }

        var existingUserId = UUID.fromString(userDto.getId());
        return tokenRepository.generateToken(existingUserId, "email_verify", 43200);
    }
}
