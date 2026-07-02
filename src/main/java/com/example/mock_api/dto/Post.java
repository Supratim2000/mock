package com.example.mock_api.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Post extends PostRequest {
    private Instant createdAt;
}
