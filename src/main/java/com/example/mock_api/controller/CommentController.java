package com.example.mock_api.controller;

import com.example.mock_api.dto.Comment;
import com.example.mock_api.dto.CommentRequest;
import com.example.mock_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.commentOnPostById(commentRequest);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/retrieve/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId) {
        return ResponseEntity.ok(commentService.fetchAllCommentsByPostId(postId));
    }

    @GetMapping("/retrieve/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String commentId) {
        Comment comment = commentService.fetchCommentById(commentId);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/update")
    public ResponseEntity<Comment> updateComment(@RequestBody CommentRequest commentRequest) {
        Comment updatedComment = commentService.updateComment(commentRequest);

        if (updatedComment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        boolean deleted = commentService.deleteComment(commentId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/post/{postId}")
    public ResponseEntity<Void> deleteCommentsByPostId(@PathVariable String postId) {
        commentService.deleteCommentsByPostId(postId);

        return ResponseEntity.noContent().build();
    }
}