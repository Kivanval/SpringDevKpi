package com.example.springdevkpi.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Topic} entity
 */
public record TopicPayload(Long id, String title, String description,
                           LocalDateTime createdAt, Long creatorId) implements Serializable {
}