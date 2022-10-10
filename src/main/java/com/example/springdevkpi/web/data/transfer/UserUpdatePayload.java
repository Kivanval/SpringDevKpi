package com.example.springdevkpi.web.data.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdatePayload {
    @Size(min = 5, max = 255) @Pattern(regexp = "^\\S+$") String username;
    @Size(min = 8, max = 255) @Pattern(regexp = "^\\S+$") String password;
    @Size(min = 3, max = 255) @Pattern(regexp = "^\\S+$") String roleName;
}
