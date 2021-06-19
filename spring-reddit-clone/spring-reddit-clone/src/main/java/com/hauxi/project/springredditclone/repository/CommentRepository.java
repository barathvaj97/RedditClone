package com.hauxi.project.springredditclone.repository;

import java.util.List;

import com.hauxi.project.springredditclone.model.Comments;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Long>{
        List<Comments> findByPost(Post post);

    List<Comments> findAllByUser(Users user);
}