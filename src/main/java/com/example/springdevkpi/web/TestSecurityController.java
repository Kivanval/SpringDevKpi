package com.example.springdevkpi.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityController {

    @GetMapping("/test")
    public String getAdmin() {
        return "Test";
    }
}
