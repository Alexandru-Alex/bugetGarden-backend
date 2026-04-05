package com.example.bugetGarden.service.authorization.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "google")
public class GoogleProperties {

    private String authorizationUrl;
    private String userInfoUrl;

}
