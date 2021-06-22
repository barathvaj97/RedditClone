package com.hauxi.project.springredditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.hauxi.project.springredditclone.dto.PostRequest;
import com.hauxi.project.springredditclone.dto.PostResponse;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.SubReddit;
import com.hauxi.project.springredditclone.model.Users;
import com.hauxi.project.springredditclone.model.Vote;
import com.hauxi.project.springredditclone.model.VoteType;
import com.hauxi.project.springredditclone.repository.CommentRepository;
import com.hauxi.project.springredditclone.repository.VoteRepository;
import com.hauxi.project.springredditclone.service.AuthService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static com.hauxi.project.springredditclone.model.VoteType.DOWNVOTE;
import static com.hauxi.project.springredditclone.model.VoteType.UPVOTE;

import java.util.Optional;


/*
 PostMapper Interface
 
@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createDate", expression = "java((java.time.Instant.now()))")
    @Mapping(target = "subReddit", source = "subReddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, SubReddit subReddit, Users user);

    @Mapping(target = "id", source="postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "url",source = "url")
    @Mapping(target = "subRedditName", source = "subReddit.name")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapToDto(Post post);
}

*/

@Mapper(componentModel = "spring")
public abstract class PostMapper{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subReddit", source = "subReddit")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, SubReddit subReddit, Users user);

// @Mapping(target = "user", source = "user")
//ALWAYS CHECK SPELLINGS IN TARGET AND SOURCE FROM DTO AND MODEL
// IN CASE THEY DONT MATCH, we receive .NoSuchBeanDefinitionException

    @Mapping(target = "id", source="postId")
    @Mapping(target = "subRedditName", source = "subReddit.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
   @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))") 

    public abstract PostResponse mapToDto(Post post);   

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreateDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post){
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post){
        return checkVoteType(post,DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType){
        if(authService.isLoggedIn()){
            Optional<Vote> voteForPostByUser= voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
            
            return voteForPostByUser.filter(vote-> vote.getVoteType().equals(voteType)).isPresent();
        }

        return false;
    }
    
}