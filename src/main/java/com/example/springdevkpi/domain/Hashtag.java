package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Hashtag {
    private Long id;
    private String name;

    private Set<Post> posts = new HashSet<>();
}
