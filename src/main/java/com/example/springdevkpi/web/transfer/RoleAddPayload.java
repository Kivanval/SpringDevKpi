package com.example.springdevkpi.web.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAddPayload {
    @NotBlank String name;
    @Min(1) Integer rank = 1;
}
