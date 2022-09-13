package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;

    private Role role;
    private Set<Topic> topics = new HashSet<>();
    private Set<Post> posts = new HashSet<>();
}
