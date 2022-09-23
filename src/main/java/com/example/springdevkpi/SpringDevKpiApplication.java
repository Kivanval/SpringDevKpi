package com.example.springdevkpi;

import com.example.springdevkpi.service.RoleService;
import com.example.springdevkpi.service.UserService;
import com.example.springdevkpi.web.transfer.Credentials;
import com.example.springdevkpi.web.transfer.RolePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDevKpiApplication implements CommandLineRunner {


    private RoleService roleService;

    private UserService userService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this. userService =  userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDevKpiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        roleService.create(new RolePayload("USER", 1));
        roleService.create(new RolePayload("ADMIN", 2));
        userService.signUp(new Credentials("kivanval", "12345678"));
        userService.signUp(new Credentials("bhurov", "qwerty123"));
        userService.signUp(new Credentials("myryta228", "8642ruchka"));
    }
}
