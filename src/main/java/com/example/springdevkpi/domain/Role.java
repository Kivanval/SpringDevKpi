package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Role {
    private Long id;
    private String name;

    private Set<User> users = new HashSet<>();
    private Set<Role> childRoles = new HashSet<>();

    public Set<Role> getAllChildRoles() {
        Set<Role> copyChildRoles = new HashSet<>(childRoles);
        Set<Role> allChildRoles = new HashSet<>(copyChildRoles);
        while (!copyChildRoles.isEmpty()) {
            copyChildRoles = allChildRoles.stream()
                    .flatMap(role -> role.getChildRoles().stream())
                    .peek(allChildRoles::add)
                    .filter(role -> !role.getChildRoles().isEmpty())
                    .collect(Collectors.toSet());
        }
        return allChildRoles;
    }
}
