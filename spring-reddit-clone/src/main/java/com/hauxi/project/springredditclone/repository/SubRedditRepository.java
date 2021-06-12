package com.hauxi.project.springredditclone.repository;

import java.util.Optional;

import com.hauxi.project.springredditclone.model.SubReddit;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SubRedditRepository extends JpaRepository<SubReddit,Long>{
    Optional<SubReddit> findByName(String subRedditName);
}