package com.example.springdevkpi.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Post} entity
 */
public record PostPayload(Long id, String text, LocalDateTime createdAt, Long creatorId,
                          Long topicId) implements Serializable {
}