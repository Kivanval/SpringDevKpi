package com.example.springdevkpi.web.data.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Role} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleUpdatePayload {
    @Size(min = 3, max = 255) String name;
    @Min(1) Integer rank = 1;
}
