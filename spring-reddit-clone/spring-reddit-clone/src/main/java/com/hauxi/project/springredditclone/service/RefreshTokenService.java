package com.hauxi.project.springredditclone.service;

import java.time.Instant;
import java.util.UUID;

import com.hauxi.project.springredditclone.exception.RedditException;
import com.hauxi.project.springredditclone.model.RefreshToken;
import com.hauxi.project.springredditclone.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken= new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedTime(Instant.now());
        
        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
        .orElseThrow(()-> new RedditException("Invalid Refresh Token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }

}
