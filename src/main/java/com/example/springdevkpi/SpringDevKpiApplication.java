package com.example.springdevkpi;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.domain.Role;
import com.example.springdevkpi.service.RoleService;
import com.example.springdevkpi.web.dto.RolePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDevKpiApplication implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDevKpiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        roleService.create(new RolePayload("USER", 1));
        roleService.create(new RolePayload("ADMIN", 2));
    }
}
