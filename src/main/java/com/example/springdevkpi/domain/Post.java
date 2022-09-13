package com.example.springdevkpi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Post {
    private Long id;
    private String text;
    private LocalDateTime createdAt;

    private User creator;
    private Topic topic;
}
