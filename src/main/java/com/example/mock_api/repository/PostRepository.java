package com.example.mock_api.repository;

import com.example.mock_api.entity.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findAllByOrderByCreatedAtDesc();
}
