package com.social.like;

import com.social.like.model.Like;
import com.social.like.repository.LikeRepository;
import com.social.like.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceApplicationTests {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    private UUID testUserId;
    private UUID testPostId;
    private Like testLike;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testPostId = UUID.randomUUID();
        testLike = new Like();
        testLike.setUserId(testUserId);
        testLike.setPostId(testPostId);
    }

    @Test
    void toggleLike_AddLike_Success() {
        when(likeRepository.findByUserIdAndPostId(testUserId, testPostId)).thenReturn(Optional.empty());
        when(likeRepository.save(any(Like.class))).thenReturn(testLike);

        Map<String, Object> response = likeService.toggleLike(testUserId, testPostId);

        assertEquals("Like added", response.get("message"));
        assertEquals(true, response.get("liked"));
    }

    @Test
    void toggleLike_RemoveLike_Success() {
        when(likeRepository.findByUserIdAndPostId(testUserId, testPostId)).thenReturn(Optional.of(testLike));
        doNothing().when(likeRepository).delete(testLike);

        Map<String, Object> response = likeService.toggleLike(testUserId, testPostId);

        assertEquals("Like removed", response.get("message"));
        assertEquals(false, response.get("liked"));
        verify(likeRepository, times(1)).delete(testLike);
    }

    @Test
    void getLikeCount_Success() {
        when(likeRepository.countByPostId(testPostId)).thenReturn(5L);

        Map<String, Object> response = likeService.getLikeCount(testPostId);

        assertEquals(testPostId, response.get("postId"));
        assertEquals(5L, response.get("likeCount"));
    }
}