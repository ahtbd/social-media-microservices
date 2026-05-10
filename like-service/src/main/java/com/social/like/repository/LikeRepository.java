package com.social.like.repository;

import com.social.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    
    List<Like> findByPostId(UUID postId);
    
    Optional<Like> findByUserIdAndPostId(UUID userId, UUID postId);
    
    long countByPostId(UUID postId);
    
    boolean existsByUserIdAndPostId(UUID userId, UUID postId);
}