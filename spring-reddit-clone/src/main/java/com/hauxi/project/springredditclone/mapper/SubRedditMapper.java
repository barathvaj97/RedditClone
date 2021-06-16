package com.hauxi.project.springredditclone.mapper;

import java.util.List;

import com.hauxi.project.springredditclone.dto.SubRedditDto;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.SubReddit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/* 
    annotated the SubredditMapper with @Mapper(componentModel=’spring’) annotation to specify that 
    this interface is a Mapstruct Mapper and Spring should identify  it as a component and should be
    able to inject it into other components like SubredditService 
*/

@Mapper(componentModel = "spring")
public  interface SubRedditMapper {

    @Mapping(target = "numberOfPosts", expression =  "java(mapPosts(subReddit.getPosts()))")
    SubRedditDto mapSubRedditToDto (SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditDto subRedditDto);
}

/*
    can create reverse mappings from SubredditDto to Subreddit by annotating a method with InheritInverseConfiguration.
    This annotation reverse’s the mapping which exists to convert from Subreddit to SubredditDto
 */
