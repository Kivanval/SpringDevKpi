package com.example.springdevkpi.web.transfer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credentials implements Serializable {
    @NotNull @Size(min = 5, max = 255) @Pattern(regexp = "^\\S+$") String username;
    @NotNull @Size(min = 8, max = 255) @Pattern(regexp = "^\\S+$") String password;
}