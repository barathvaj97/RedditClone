package com.hauxi.project.springredditclone.service;

import java.util.Optional;

import com.hauxi.project.springredditclone.dto.VoteDto;
import com.hauxi.project.springredditclone.exception.PostNotFoundException;
import com.hauxi.project.springredditclone.exception.RedditException;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.Vote;
import com.hauxi.project.springredditclone.model.VoteType;
import com.hauxi.project.springredditclone.repository.PostRepository;
import com.hauxi.project.springredditclone.repository.VoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote (VoteDto voteDto){
        Post post= postRepository.findById(voteDto.getPostId())
        .orElseThrow(()-> new PostNotFoundException("Post not found with ID- "+ voteDto.getPostId()));
        
        Optional<Vote> voteByPostAndUser= voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RedditException("You have already "+ voteDto.getVoteType()+ "'d for this post.");
        }
        
        if(VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else{
            post.setVoteCount(post.getVoteCount()- 1);
        }

        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
        .voteType(voteDto.getVoteType())
        .post(post)
        .user(authService.getCurrentUser())
        .build();
    }
    
}
