package com.example.mock_api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Document(collection = "comments")
public class CommentEntity {
    @Id
    private String id;

    @Indexed
    private String postId;

    private String comment;
    private Instant createdAt;
}
