package com.example.springdevkpi.service;

import com.example.springdevkpi.web.transfer.Credentials;
import com.example.springdevkpi.web.transfer.RoleAddPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements CommandLineRunner {

    private final RoleService roleService;

    private final UserService userService;

    @Autowired
    public LoadData(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        roleService.create(new RoleAddPayload("USER", 1));
        roleService.create(new RoleAddPayload("ADMIN", 2));
        userService.signUp(new Credentials("kivanval", "12345678"));
        userService.signUp(new Credentials("bhurov", "qwerty123"));
        userService.signUp(new Credentials("myryta228", "8642ruchka"));
    }
}
