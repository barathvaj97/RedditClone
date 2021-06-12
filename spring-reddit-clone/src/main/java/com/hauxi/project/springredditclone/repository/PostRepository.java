package com.hauxi.project.springredditclone.repository;

import java.util.List;

import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.SubReddit;
import com.hauxi.project.springredditclone.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findAllBySubreddit(SubReddit subreddit);

    List<Post> findByUser(Users user);
}
