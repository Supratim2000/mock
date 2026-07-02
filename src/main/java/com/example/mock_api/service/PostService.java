package com.example.mock_api.service;

import com.example.mock_api.dto.Post;
import com.example.mock_api.dto.PostRequest;
import com.example.mock_api.dto.Product;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final ArrayList<Post> postList = new ArrayList<Post>();

    public List<Post> fetchAllPosts() {
        return postList;
    }

    public Post fetchPostById(String _id) {
        return postList.stream()
                .filter(post -> post.getId().equals(_id))
                .findFirst()
                .orElse(null);
    }

    public Post updatePost(PostRequest postRequest) {
        Post fetchedPost = fetchPostById(postRequest.getId());

        if(fetchedPost == null) {
            return null;
        }

        fetchedPost.setUserName(postRequest.getUserName());
        fetchedPost.setUserAvatar(postRequest.getUserAvatar());
        fetchedPost.setPostImage(postRequest.getPostImage());
        fetchedPost.setCaption(postRequest.getCaption());
        fetchedPost.setLikes(postRequest.getLikes());

        return fetchedPost;
    }

    public Post createPost(PostRequest postRequest) {
        Post fetchedPost = fetchPostById(postRequest.getId());

        if(fetchedPost != null) {
            return null;
        }

        Post createdPost = Post.builder()
                .id(postRequest.getId())
                .userName(postRequest.getUserName())
                .userAvatar(postRequest.getUserAvatar())
                .postImage(postRequest.getPostImage())
                .caption(postRequest.getCaption())
                .likes(postRequest.getLikes())
                .commentsCount(postRequest.getCommentsCount())
                .createdAt(Instant.now())
                .build();

        postList.add(createdPost);

        return createdPost;
    }

    public boolean deletePostById(String _id) {
        Post existingPost = fetchPostById(_id);

        if (existingPost == null) {
            return false;
        }

        return postList.remove(existingPost);
    }
}
