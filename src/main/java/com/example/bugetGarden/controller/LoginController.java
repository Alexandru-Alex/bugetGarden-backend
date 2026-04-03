package com.example.bugetGarden.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @PostMapping("login")
    public void receiveEmail(@RequestBody GoogleToken token) {
        log.info("TOKEN ESTE: {} ", token);
    }

}
