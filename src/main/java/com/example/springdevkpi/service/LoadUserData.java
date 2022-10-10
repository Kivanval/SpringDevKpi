package com.example.springdevkpi.service;

import com.example.springdevkpi.web.transfer.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class LoadUserData implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public LoadUserData(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.signUp(new Credentials("kivanval", "12345678"));
        userService.signUp(new Credentials("bhurov", "qwerty123"));
        userService.signUp(new Credentials("mykyta228", "8642ruchka"));
    }

}
