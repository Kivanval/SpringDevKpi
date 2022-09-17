package com.example.springdevkpi.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

public record TopicBasePayload(
        @Size(min = 3, max = 255) String title,
        @Size(min = 1, max = 255) String description,
        @Min(1) Long creatorId) implements Serializable {
}
