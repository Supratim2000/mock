package com.example.mock_api.repository;

import com.example.mock_api.entity.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findByPostId(String postId);
    void deleteByPostId(String postId);
}
