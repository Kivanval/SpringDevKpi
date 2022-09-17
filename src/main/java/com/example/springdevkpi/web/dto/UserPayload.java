package com.example.springdevkpi.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.User} entity
 */
public record UserPayload(Long id, String username, LocalDateTime createdAt, Long roleId) implements Serializable {

}