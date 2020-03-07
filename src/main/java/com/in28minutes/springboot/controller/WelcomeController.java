package com.in28minutes.springboot.controller;

import com.in28minutes.springboot.configuration.BasicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @Value("${welcome.message}")
    private String message;

    @Value("${welcome2.message}")
    private String message2;

    @Autowired
    private BasicConfiguration basicConfiguration;

    @RequestMapping("/welcome")
    public String welcome() {
        return message;
    }

    @GetMapping("/welcome2")
    public String goodbay() {
        return message2;
    }

    @GetMapping("/dynamic-configuration")
    private Map dynamicConfiguration() {
        Map map = new HashMap();
        map.put("message",basicConfiguration.getMessage());
        map.put("number", basicConfiguration.getNumber());
        map.put("value", basicConfiguration.isValue());
        return map;
    }
}
