package com.example.springdevkpi.service;


import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.domain.Role;
import com.example.springdevkpi.web.dto.RolePayload;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Delegate
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean create(RolePayload payload) {
        var role = new Role();
        role.setName(payload.getName());
        role.setRank(payload.getRank());
        roleRepository.save(role);
        return true;
    }
}
