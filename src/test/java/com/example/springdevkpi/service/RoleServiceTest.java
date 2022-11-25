package com.example.springdevkpi.service;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import com.example.springdevkpi.web.data.transfer.RoleAddPayload;
import com.example.springdevkpi.web.data.transfer.RoleUpdatePayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static com.example.springdevkpi.service.TestHelper.getUserForTest;
import static org.junit.jupiter.api.Assertions.*;

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
    void shouldCreate() {
        var payload = new RoleAddPayload("TEST", 1);

        roleService.create(payload);

        assertTrue(roleRepository.existsByName("TEST"));
    }

    @Test
    void shouldSetFieldsFromPayload() {
        var payload = new RoleAddPayload("TEST", 1);

        var role = roleService.create(payload);

        assertEquals("TEST", role.getName());
        assertEquals(1, role.getRank());
    }

    @Test
    void shouldFindByName() {
        var payload = new RoleAddPayload("TEST", 1);

        roleService.create(payload);

        assertTrue(roleRepository.findByName("TEST").isPresent());
    }

    @Test
    void shouldNotFindByName() {
        var payload = new RoleAddPayload("TEST", 1);

        roleService.create(payload);

        assertTrue(roleRepository.findByName("INVALID").isEmpty());
    }

    @Test
    void shouldFindAll() {
        var payload1 = new RoleAddPayload("TEST1", 1);
        var payload2 = new RoleAddPayload("TEST2", 1);

        roleService.create(payload1);
        roleService.create(payload2);

        assertEquals(2, roleService.findAll(0, 2, "id").getSize());
    }

    @Test
    void shouldDeleteByName() {
        var payload = new RoleAddPayload("TEST", 1);

        roleService.create(payload);
        roleService.deleteByName("TEST");

        assertFalse(roleRepository.existsByName("TEST"));
    }

    @Test
    void shouldDeleteByNameAllRelatedUsers() {
        var payload = new RoleAddPayload("TEST", 1);
        var user = getUserForTest();

        var role = roleService.create(payload);
        role.addUser(user);
        roleService.deleteByName("TEST");

        assertFalse(userRepository.existsByUsername("TESTER"));
    }

    @Test
    void shouldUpdateNameAndRankFromPayload() {
        var payload = new RoleAddPayload("TEST", 1);
        var updatePayload = new RoleUpdatePayload("NEW_TEST", 2);

        roleService.create(payload);
        var isUpdated = roleService.update(updatePayload, "TEST");
        var updatedRole = roleService.findByName("NEW_TEST")
                .orElseThrow(AssertionError::new);

        assertTrue(isUpdated);
        assertEquals("NEW_TEST", updatedRole.getName());
        assertEquals(2, updatedRole.getRank());
    }
}