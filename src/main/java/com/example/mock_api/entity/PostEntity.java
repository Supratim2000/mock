package com.example.mock_api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Document(collection = "posts")
public class PostEntity {
    @Id
    private String id;

    private String userName;
    private String userAvatar;
    private String postImage;
    private String caption;
    private long likes;
    private long commentsCount;
    private Instant createdAt;
}
