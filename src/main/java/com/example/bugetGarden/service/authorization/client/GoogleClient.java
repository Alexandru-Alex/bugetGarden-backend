package com.example.bugetGarden.service.authorization.client;

import com.example.bugetGarden.dto.google.GoogleInfoDto;
import com.example.bugetGarden.service.authorization.properties.GoogleProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.example.bugetGarden.utils.FetchUtils.retryable;
import static com.example.bugetGarden.utils.FetchUtils.timing;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(GoogleProperties.class)
public class GoogleClient {

    private final GoogleProperties properties;

    public GoogleInfoDto getTokenInfo(String token) {
        var response = verifyNow(token);
        var info = getInfoNow(token);

        return GoogleInfoDto
                .builder()
                .name(info.getName())
                .id(info.getSub())
                .email(response.getEmail())
                .verifiedEmail(response.getEmailVerified())
                .expiresAt(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(Long.parseLong(response.getExp().toString())),
                        ZoneId.systemDefault()))
                .build();

    }

    public GoogleTokenInfoResponse verifyNow(String token) {
        var http = new RestTemplate();
        var uri = UriComponentsBuilder.fromUriString(properties.getAuthorizationUrl())
                .queryParam("access_token", token)
                .build()
                .toUri();
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        var request = new HttpEntity<>(headers);
        var action = "Verify google token";

        return retryable(action).execute(a -> runFetch(action, http, uri, request).getBody());
    }

    private ResponseEntity<GoogleTokenInfoResponse> runFetch(String action, RestTemplate http, URI uri, HttpEntity<?> request) {
        return timing(this, action, () -> http.postForEntity(uri, request, GoogleTokenInfoResponse.class));
    }

    private ResponseEntity<GoogleAccountInfoResponse> runFetchInfo(String action, RestTemplate http, URI uri, HttpEntity<?> request) {
        return timing(this, action, () -> http.postForEntity(uri, request, GoogleAccountInfoResponse.class));
    }

    public GoogleAccountInfoResponse getInfoNow(String token) {
        var http = new RestTemplate();
        var uri = UriComponentsBuilder.fromUriString(properties.getUserInfoUrl())
                .build()
                .toUri();
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(APPLICATION_JSON);
        var request = new HttpEntity<>(headers);
        var action = "Get google info";

        return retryable(action).execute(a -> runFetchInfo(action, http, uri, request).getBody());
    }
}
