package com.example.bugetGarden.service;

import com.example.bugetGarden.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    @Autowired
    private  EmailRepository repository;

    public void process(String email) {
        repository.save(email);
    }
}
