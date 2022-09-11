package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Post {
    private Long id;
    private String text;

    private User creator;
    private Topic topic;
    private Set<Hashtag> hashtags = new HashSet<>();
}
