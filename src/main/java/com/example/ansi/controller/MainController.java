package com.example.ansi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost"}, allowedHeaders = "*", allowCredentials = "true")
public class MainController {
    @GetMapping("/")
    public String testGet() {
        return "TEST";
    }
}
