package com.example.springdevkpi.service.loader;

import com.example.springdevkpi.service.RoleService;
import com.example.springdevkpi.web.data.transfer.RoleAddPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleLoader implements CommandLineRunner {
    private final RoleService roleService;

    @Autowired
    public RoleLoader(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        roleService.create(new RoleAddPayload("USER", 1));
        roleService.create(new RoleAddPayload("ADMIN", 2));
        roleService.create(new RoleAddPayload("SUPER_ADMIN", 3));
    }
}
