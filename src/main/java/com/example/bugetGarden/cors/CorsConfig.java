package com.example.bugetGarden.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")        // toate endpoint-urile
                        .allowedOrigins("*")      // orice domeniu
                        .allowedMethods("*")      // orice metodă: GET, POST, PUT, DELETE, OPTIONS
                        .allowedHeaders("*")      // orice header
                        .allowCredentials(false); // nu folosi credentials când "*" e setat
            }
        };
    }
}
