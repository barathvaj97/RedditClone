package com.hauxi.project.springredditclone.repository;

import java.util.Optional;

import com.hauxi.project.springredditclone.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
