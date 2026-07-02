package com.example.mock_api.service;

import com.example.mock_api.dto.Comment;
import com.example.mock_api.dto.CommentRequest;
import com.example.mock_api.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private List<Comment> commentList = new ArrayList<Comment>();
    private final PostService postService;

    public Comment commentOnPostById(String _postId, CommentRequest commentRequest) {
        Post fetchedPost = postService.fetchPostById(_postId);
        if(fetchedPost == null) {
            return null;
        }

        Comment comment = Comment.builder()
                .id(commentRequest.getId())
                .postId(fetchedPost.getId())
                .comment(commentRequest.getComment())
                .createdAt(Instant.now())
                .build();

        commentList.add(comment);

        return comment;
    }

    public List<Comment> fetchAllCommentsByPostId(String _postId) {
        return commentList.stream()
                .filter(comment -> comment.getPostId().equals(_postId))
                .toList();
    }

    public Comment fetchCommentById(String commentId) {
        return commentList.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElse(null);
    }

    public Comment updateComment(CommentRequest commentRequest) {
        Comment existingComment = fetchCommentById(commentRequest.getId());

        if (existingComment == null) {
            return null;
        }

        existingComment.setComment(commentRequest.getComment());

        return existingComment;
    }

    public boolean deleteComment(String commentId) {

        Comment existingComment = fetchCommentById(commentId);

        if (existingComment == null) {
            return false;
        }

        Post post = postService.fetchPostById(existingComment.getPostId());

        if (post != null && post.getCommentsCount() > 0) {
            post.setCommentsCount(post.getCommentsCount() - 1);
        }

        return commentList.remove(existingComment);
    }

    public void deleteCommentsByPostId(String postId) {

        Post post = postService.fetchPostById(postId);

        if (post != null) {
            post.setCommentsCount(0);
        }

        commentList.removeIf(comment -> comment.getPostId().equals(postId));
    }
}
