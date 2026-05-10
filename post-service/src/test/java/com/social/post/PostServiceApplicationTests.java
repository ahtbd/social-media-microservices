package com.social.post;

import com.social.post.dto.PostRequest;
import com.social.post.dto.PostResponse;
import com.social.post.model.Post;
import com.social.post.repository.PostRepository;
import com.social.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceApplicationTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post testPost;
    private UUID testPostId;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testPostId = UUID.randomUUID();
        testUserId = UUID.randomUUID();
        testPost = new Post();
        testPost.setId(testPostId);
        testPost.setUserId(testUserId);
        testPost.setContent("Test post content");
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createPost_Success() {
        PostRequest request = new PostRequest();
        request.setUserId(testUserId);
        request.setContent("Test post content");

        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        PostResponse response = postService.createPost(request);

        assertNotNull(response);
        assertEquals(testPost.getUserId(), response.getUserId());
        assertEquals(testPost.getContent(), response.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getPostById_Success() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));

        PostResponse response = postService.getPostById(testPostId);

        assertNotNull(response);
        assertEquals(testPostId, response.getId());
    }

    @Test
    void getAllPosts_Success() {
        when(postRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(testPost));

        List<PostResponse> responses = postService.getAllPosts();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(testPost.getContent(), responses.get(0).getContent());
    }
}