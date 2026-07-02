package com.example.mock_api.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostRequest {
    private String id;
    private String userName;
    private String userAvatar;
    private String postImage;
    private String caption;
    private long likes;
    private long commentsCount;
}
