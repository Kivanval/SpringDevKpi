package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Topic {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    private User creator;
    private Set<Hashtag> hashtags = new HashSet<>();
    private Set<Post> posts = new HashSet<>();
}
