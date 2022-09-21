package com.example.springdevkpi.service;


import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.domain.Role;
import com.example.springdevkpi.web.transfer.RolePayload;
import com.example.springdevkpi.web.transfer.RoleUpdatePayload;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

    public boolean update(RoleUpdatePayload payload, String name) {
        var optRole = roleRepository.findByName(name);
        if (optRole.isPresent()) {
            var role = optRole.get();
            if (payload.getName() != null) {
                role.setName(payload.getName());
            }
            if (payload.getRank() != null) {
                role.setRank(payload.getRank());
            }
            return true;
        }
        log.warn("Role by name {} doesn't exists", name);
        return false;
    }
}
