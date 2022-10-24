package com.example.springdevkpi.web.data.transfer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Post} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPayload implements Serializable {
    Long id;
    String text;
    String creatorUsername;
    Long topicId;
    LocalDateTime createdAt;
}