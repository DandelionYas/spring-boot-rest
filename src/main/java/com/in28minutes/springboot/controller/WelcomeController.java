package com.in28minutes.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${welcome.message}")
    private String message;

    @Value("${welcome2.message}")
    private String message2;

    @RequestMapping("/welcome")
    public String welcome() {
        return message;
    }

    @GetMapping("/welcome2")
    public String goodbay() {
        return message2;
    }
}
