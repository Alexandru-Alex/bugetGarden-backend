package com.example.bugetGarden.service.authorization.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleTokenInfoResponse {

    private String azp;
    private String aud;
    private String sub;
    private String scope;
    private String name;
    private Long exp;

    @JsonProperty("expires_in")
    private Long expiresIn;

    private String email;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("access_type")
    private String accessType;
}
