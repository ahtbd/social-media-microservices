package com.social.post.service;

import com.social.post.dto.PostRequest;
import com.social.post.dto.PostResponse;
import com.social.post.model.Post;
import com.social.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse createPost(PostRequest request) {
        Post post = new Post();
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        post = postRepository.save(post);
        return mapToResponse(post);
    }

    public PostResponse getPostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToResponse(post);
    }

    public List<PostResponse> getPostsByUser(UUID userId) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : posts) {
            responses.add(mapToResponse(post));
        }
        return responses;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : posts) {
            responses.add(mapToResponse(post));
        }
        return responses;
    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
    }

    private PostResponse mapToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setUserId(post.getUserId());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        return response;
    }
}