package com.example.springdevkpi.web.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */
public record RolePayload(Long id, String name) implements Serializable {
}