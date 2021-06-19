package com.hauxi.project.springredditclone.repository;

import java.util.Optional;

import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.Users;
import com.hauxi.project.springredditclone.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long>{
Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, Users currentUser);
}
