package com.example.springdevkpi.service;


import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.domain.Role;
import com.example.springdevkpi.web.data.transfer.RoleAddPayload;
import com.example.springdevkpi.web.data.transfer.RoleUpdatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role create(RoleAddPayload payload) {
        var role = new Role();
        role.setName(payload.getName().toUpperCase());
        role.setRank(payload.getRank());;
        return roleRepository.save(role);
    }

    @Transactional
    public boolean update(RoleUpdatePayload payload, String name) {
        var optRole = roleRepository.findByName(name);
        if (optRole.isPresent()) {
            var role = optRole.get();
            if (payload.getName() != null) {
                role.setName(payload.getName().toUpperCase());
            }
            if (payload.getRank() != null) {
                role.setRank(payload.getRank());
            }
            roleRepository.save(role);
            return true;
        }
        log.warn("Role by name {} doesn't exists", name);
        return false;
    }

    @Transactional
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public void deleteByName(String name) {
        if (roleRepository.existsByName(name))
            this.roleRepository.deleteByName(name);
    }

    @Transactional
    public Page<Role> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(sortBy).descending());
        return roleRepository.findAll(pageable);
    }
}
