package com.example.springdevkpi.web.dto;

import javax.validation.constraints.Size;

public record Credentials(
        @Size(min = 5, max = 255) String username,
        @Size(min = 8, max = 255) String password) {
}