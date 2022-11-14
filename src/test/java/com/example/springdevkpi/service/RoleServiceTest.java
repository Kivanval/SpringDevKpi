package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.web.data.transfer.RoleAddPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void shouldCreateRoleWithPayloadFieldsAndFindByName() {
        var payload = new RoleAddPayload("TEST", 1);
        roleService.create(payload);
        var maybeRole = roleService.findByName("TEST");

        assertTrue(maybeRole.isPresent());

        var role = maybeRole.get();

        assertEquals("TEST", role.getName());
        assertEquals(1, role.getRank());
    }

    @Test
    void shouldNotFindByName() {
        var payload = new RoleAddPayload("TEST", 1);
        roleService.create(payload);

        assertTrue(roleService.findByName("INVALID").isEmpty());
    }

    @Test
    void shouldDeleteByName() {
        var payload = new RoleAddPayload("TEST", 1);
        roleService.create(payload);
        roleService.deleteByName("TEST");

        assertTrue(roleService.findByName("TEST").isEmpty());
    }

    @Test
    void shouldFindAll() {
        var payload1 = new RoleAddPayload("TEST1", 1);
        var payload2 = new RoleAddPayload("TEST2", 1);
        roleService.create(payload1);
        roleService.create(payload2);

        assertEquals(2, roleService.findAll(0, 2, "id").getSize());
    }
}