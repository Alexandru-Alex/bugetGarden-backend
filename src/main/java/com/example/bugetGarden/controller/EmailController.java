package com.example.bugetGarden.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    @PostMapping("email-add")
    public void receiveEmail(@RequestParam String email){
        System.out.println("email primit: "+email);
    }


}
