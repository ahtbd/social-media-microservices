package com.social.post.controller;

import com.social.post.dto.PostRequest;
import com.social.post.dto.PostResponse;
import com.social.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable UUID userId) {
        List<PostResponse> responses = postService.getPostsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok(Map.of("message", "Post deleted successfully"));
    }
}