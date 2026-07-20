package com.example.mock_api.service;

import com.example.mock_api.dto.Comment;
import com.example.mock_api.dto.CommentRequest;
import com.example.mock_api.entity.CommentEntity;
import com.example.mock_api.entity.PostEntity;
import com.example.mock_api.repository.CommentRepository;
import com.example.mock_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    public Comment commentOnPostById(CommentRequest commentRequest) {
        PostEntity fetchedPost = postService.fetchPostById(commentRequest.getPostId());
        if(fetchedPost == null) {
            return null;
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .id(commentRequest.getId())
                .postId(fetchedPost.getId())
                .comment(commentRequest.getComment())
                .createdAt(Instant.now())
                .build();

        CommentEntity savedComment = commentRepository.save(commentEntity);

        fetchedPost.setCommentsCount(fetchedPost.getCommentsCount() + 1);
        postRepository.save(fetchedPost);

        return mapToDto(savedComment);
    }

    public List<Comment> fetchAllCommentsByPostId(String _postId) {
        return commentRepository.findByPostId(_postId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public Comment fetchCommentById(String commentId) {
        return commentRepository.findById(commentId)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Transactional
    public Comment updateComment(CommentRequest commentRequest) {
        CommentEntity fetchedComment = commentRepository.findById(commentRequest.getId()).orElse(null);
        if(fetchedComment == null) {
            return null;
        }

        fetchedComment.setComment(commentRequest.getComment());

        String oldPostId = fetchedComment.getPostId();
        String newPostId = commentRequest.getPostId();

        if(!oldPostId.equals(newPostId)) {
            PostEntity oldPost = postService.fetchPostById(oldPostId);
            PostEntity newPost = postService.fetchPostById(newPostId);

            if(newPost == null || oldPost == null) {
                return null;
            }

            if(oldPost.getCommentsCount() > 0) {
                oldPost.setCommentsCount(oldPost.getCommentsCount() - 1);
            }
            newPost.setCommentsCount(newPost.getCommentsCount() + 1);

            postRepository.save(oldPost);
            postRepository.save(newPost);

            fetchedComment.setPostId(newPostId);
        }

        CommentEntity savedComment = commentRepository.save(fetchedComment);

        return mapToDto(savedComment);
    }

    @Transactional
    public boolean deleteComment(String commentId) {
        CommentEntity fetchedComment = commentRepository.findById(commentId).orElse(null);
        if(fetchedComment == null) {
            return false;
        }

        commentRepository.delete(fetchedComment);

        PostEntity fetchedPost = postService.fetchPostById(fetchedComment.getPostId());

        if(fetchedPost != null && fetchedPost.getCommentsCount() > 0) {
            fetchedPost.setCommentsCount(fetchedPost.getCommentsCount() - 1);
            postRepository.save(fetchedPost);
        }

        return true;
    }

    @Transactional
    public void deleteCommentsByPostId(String postId) {
        List<CommentEntity> fetchedComments = commentRepository.findByPostId(postId);
        commentRepository.deleteAll(fetchedComments);

        PostEntity fetchedPost = postService.fetchPostById(postId);
        if(fetchedPost != null) {
            fetchedPost.setCommentsCount(0);
            postRepository.save(fetchedPost);
        }
    }

    private Comment mapToDto(CommentEntity entity) {
        return Comment.builder()
                .id(entity.getId())
                .postId(entity.getPostId())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
