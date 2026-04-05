package com.example.bugetGarden.service;


import com.example.bugetGarden.controller.GoogleToken;
import com.example.bugetGarden.service.authorization.client.GoogleClient;
import com.example.bugetGarden.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationService {

    private final GoogleClient client;
    private final UserService userService;

    public String authorizeAndRegisterToken(GoogleToken token) {
        var tokenInfo = client.getTokenInfo(token.getToken());
        return userService.registerGoogleUserIfNecessary(tokenInfo);
    }
}
