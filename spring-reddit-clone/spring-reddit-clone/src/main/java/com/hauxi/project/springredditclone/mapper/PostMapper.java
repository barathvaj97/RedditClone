package com.hauxi.project.springredditclone.mapper;

import com.hauxi.project.springredditclone.dto.PostRequest;
import com.hauxi.project.springredditclone.dto.PostResponse;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.SubReddit;
import com.hauxi.project.springredditclone.model.Users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * PostMapper
 */ 
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