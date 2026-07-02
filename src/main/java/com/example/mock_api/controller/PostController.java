package com.example.mock_api.controller;

import com.example.mock_api.dto.Post;
import com.example.mock_api.dto.PostRequest;
import com.example.mock_api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> fetchAllPosts() {
        return ResponseEntity.ok(postService.fetchAllPosts());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Post> fetchPostById(@PathVariable String id) {
        Post post = postService.fetchPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
        Post createdPost = postService.createPost(postRequest);
        if (createdPost == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody PostRequest postRequest) {
        Post updatedPost = postService.updatePost(postRequest);
        if (updatedPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable String id) {
        boolean deleted = postService.deletePostById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
