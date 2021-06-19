package com.hauxi.project.springredditclone.mapper;

import com.hauxi.project.springredditclone.dto.CommentsDto;
import com.hauxi.project.springredditclone.model.Comments;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.Users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post",source = "post")
    Comments map(CommentsDto commentsDto, Post post,Users user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comments comment);
}
