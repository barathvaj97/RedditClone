package com.hauxi.project.springredditclone.controller;

import java.util.List;

import com.hauxi.project.springredditclone.dto.CommentsDto;
import com.hauxi.project.springredditclone.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {
    
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }

}
    // @PostMapping
    // public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
    //     commentService.createComment(commentsDto);
    //     return new ResponseEntity<>(CREATED);
    // }

    // @GetMapping
    // public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam("postId") Long postId){
    //     return status(OK)
    //     .body(commentService.getCommentByPost(postId));
    // }

    // @GetMapping
    // public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@RequestParam("username") String username){
    //     return status(OK).body(commentService.getCommentsByUser(username));
    // }

