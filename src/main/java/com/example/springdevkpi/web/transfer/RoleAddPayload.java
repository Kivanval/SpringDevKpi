package com.example.springdevkpi.web.transfer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAddPayload {
    @NotBlank @Pattern(regexp = "^\\S+$") String name;
    @Min(1) Integer rank = 1;
}
