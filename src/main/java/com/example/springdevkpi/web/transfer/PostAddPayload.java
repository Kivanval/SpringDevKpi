package com.example.springdevkpi.web.transfer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Post} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostAddPayload implements Serializable {
    @NotBlank String text;
    @NotNull @Size(min = 5, max = 255) @Pattern(regexp = "^\\S+$") String creatorUsername;
    @Min(1) Long topicId;
}