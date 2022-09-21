package com.example.springdevkpi.web.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.springdevkpi.domain.Topic} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicUpdatePayload implements Serializable {
    @Size(min = 3, max = 255) String title;
    @Size(min = 1, max = 255) String description;
}
