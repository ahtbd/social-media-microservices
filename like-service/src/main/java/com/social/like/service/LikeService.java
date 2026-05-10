package com.social.like.service;

import com.social.like.model.Like;
import com.social.like.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Map<String, Object> toggleLike(UUID userId, UUID postId) {
        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
        
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Like removed");
            response.put("liked", false);
            return response;
        } else {
            Like like = new Like();
            like.setUserId(userId);
            like.setPostId(postId);
            likeRepository.save(like);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Like added");
            response.put("liked", true);
            return response;
        }
    }

    public Map<String, Object> getLikeCount(UUID postId) {
        long count = likeRepository.countByPostId(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("likeCount", count);
        return response;
    }

    public List<UUID> getUsersWhoLiked(UUID postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        List<UUID> userIds = new ArrayList<>();
        for (Like like : likes) {
            userIds.add(like.getUserId());
        }
        return userIds;
    }
}