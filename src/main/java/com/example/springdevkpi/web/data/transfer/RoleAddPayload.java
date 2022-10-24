package com.example.springdevkpi.web.data.transfer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAddPayload {
    @NotNull @Size(min = 3, max = 255) @Pattern(regexp = "^\\S+$") String name;
    @Min(1) Integer rank = 1;
}
