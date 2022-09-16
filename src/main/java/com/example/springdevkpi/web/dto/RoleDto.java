package com.example.springdevkpi.web.dto;

import com.example.springdevkpi.domain.Role;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */
public record RoleDto(Long id, String name) implements Serializable {
}