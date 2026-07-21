package com.example.mock_api.service;

import com.example.mock_api.dto.Post;
import com.example.mock_api.dto.PostRequest;
import com.example.mock_api.dto.Product;
import com.example.mock_api.entity.PostEntity;
import com.example.mock_api.repository.CommentRepository;
import com.example.mock_api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<PostEntity> fetchAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public PostEntity fetchPostById(String _id) {
        return postRepository
                .findById(_id)
                .orElse(null);
    }

    public PostEntity updatePost(PostRequest postRequest) {
        PostEntity existingPost = postRepository.findById(postRequest.getId())
                .orElse(null);

        if (existingPost == null) {
            return null;
        }

        existingPost.setUserName(postRequest.getUserName());
        existingPost.setUserAvatar(postRequest.getUserAvatar());
        existingPost.setPostImage(postRequest.getPostImage());
        existingPost.setCaption(postRequest.getCaption());
        existingPost.setLikes(postRequest.getLikes());
        existingPost.setCommentsCount(postRequest.getCommentsCount());

        return postRepository.save(existingPost);
    }

    public PostEntity createPost(PostRequest postRequest) {
        if(postRepository.existsById(postRequest.getId())) {
            return null;
        }

        PostEntity createdPost = PostEntity.builder()
                .id(postRequest.getId())
                .userName(postRequest.getUserName())
                .userAvatar(postRequest.getUserAvatar())
                .postImage(postRequest.getPostImage())
                .caption(postRequest.getCaption())
                .likes(postRequest.getLikes())
                .commentsCount(postRequest.getCommentsCount())
                .createdAt(Instant.now())
                .build();

        return postRepository.save(createdPost);
    }

    @Transactional
    public boolean deletePostById(String _id) {
        Optional<PostEntity> post = postRepository.findById(_id);

        if (post.isEmpty()) {
            return false;
        }

        commentRepository.deleteByPostId(_id);
        postRepository.delete(post.get());

        return true;
    }
}
