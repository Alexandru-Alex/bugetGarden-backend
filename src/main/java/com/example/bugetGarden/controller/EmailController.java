package com.example.bugetGarden.controller;

import com.example.bugetGarden.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    @Autowired
    private  EmailService service;

    @PostMapping("email-add")
    public void receiveEmail(@RequestParam String email){
        service.process(email);
    }


}
