package com.social.like.controller;

import com.social.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleLike(
            @RequestParam UUID userId,
            @RequestParam UUID postId) {
        return ResponseEntity.ok(likeService.toggleLike(userId, postId));
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Map<String, Object>> getLikeCount(@PathVariable UUID postId) {
        return ResponseEntity.ok(likeService.getLikeCount(postId));
    }
}