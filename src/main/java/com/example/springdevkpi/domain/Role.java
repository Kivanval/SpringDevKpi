package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Role {
    private Long id;
    private String name;

    private Set<User> users = new HashSet<>();
}
