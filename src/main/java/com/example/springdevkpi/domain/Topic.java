package com.example.springdevkpi.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Topic {
    Long id;
    String title;
    LocalDateTime createAt;

    User creator;
    Set<Hashtag> hashtags = new HashSet<>();
    Set<Post> posts = new HashSet<>();
}
