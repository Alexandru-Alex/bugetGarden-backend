package com.example.bugetGarden.dto.google;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GoogleInfoDto {

    private String id;
    private String email;
    private boolean verifiedEmail;
    private String name;
    private LocalDateTime expiresAt;

}
