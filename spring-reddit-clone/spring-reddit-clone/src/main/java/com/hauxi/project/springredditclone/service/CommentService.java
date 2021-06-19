package com.hauxi.project.springredditclone.service;

import java.util.List;

import com.hauxi.project.springredditclone.dto.CommentsDto;
import com.hauxi.project.springredditclone.exception.PostNotFoundException;
import com.hauxi.project.springredditclone.mapper.CommentMapper;
import com.hauxi.project.springredditclone.model.Comments;
import com.hauxi.project.springredditclone.model.NotificationEmail;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.Users;
import com.hauxi.project.springredditclone.repository.CommentRepository;
import com.hauxi.project.springredditclone.repository.PostRepository;
import com.hauxi.project.springredditclone.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    //TODO: Construct Post URL
    private static final String POST_URL="";

    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    // public void createComment(CommentsDto commentsDto){
    //     Post post = postRepository.findById(commentsDto.getPostId())
    //     .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));

    //     Comment comment =commentMapper.map(commentsDto, post, authService.getCurrentUser());
    //     commentRepository.save(comment);

    //     String message=mailContentBuilder.build(post.getUser().getUsername()+"posted a comment on your post. "+ POST_URL);
        
    //     sendCommentNotification(message,post.getUser());
    // }

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comments comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on post of " +post.getUser().getUsername() + POST_URL);
        //sendCommentNotification(message, post.getUser());
        sendCommentNotification(message, /*recipient*/ post.getUser() ,/*commenter */ authService.getCurrentUser());
    }

    private void sendCommentNotification(String message, Users recipient, Users commenter){
        mailService.sendMail(new NotificationEmail(commenter.getUsername()+ " Commented on post of "+ recipient.getUsername(), recipient.getEmail(),message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        Users user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

}
    // public List<CommentsDto> getCommentByPost(Long postId){
    //     Post post= postRepository.findById(postId)
    //     .orElseThrow(()-> new PostNotFoundException(postId.toString()));

    //     return commentRepository.findByPost(post)
    //     .stream()
    //     .map(commentMapper::mapToDto)
    //     .collect(toList());
    // }

    // public List<CommentsDto> getCommentsByUser(String username){
    //     Users user= userRepository.findByUsername(username)
    //     .orElseThrow(()-> new UsernameNotFoundException(username));

    //     return commentRepository.findAllByUser(user)
    //     .stream()
    //     .map(commentMapper::mapToDto)
    //     .collect(toList());
    // }


