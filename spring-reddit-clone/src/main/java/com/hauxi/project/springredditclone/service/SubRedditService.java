package com.hauxi.project.springredditclone.service;

// import java.time.Instant;

import com.hauxi.project.springredditclone.dto.SubRedditDto;
import com.hauxi.project.springredditclone.model.SubReddit;
import com.hauxi.project.springredditclone.repository.SubRedditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import java.util.List;


import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubRedditService {
    private final SubRedditRepository subRedditRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll(){
        return subRedditRepository.findAll()
        .stream()
        .map(this::mapToDto)
        .collect(toList());
    }

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto){
        SubReddit subReddit=subRedditRepository.save(mapToSubReddit(subRedditDto));
        subRedditDto.setId(subReddit.getId());
        return subRedditDto;
    }

    @Transactional(readOnly = true)
    public SubRedditDto getSubReddit(Long id){
        SubReddit subReddit=subRedditRepository.findById(id).orElseThrow(()-> new SubRedditNotFoundException("Subreddit not found wiht id: "+ id));
        return mapToDto(subReddit);
    }

    private SubRedditDto mapToDto(SubReddit subReddit){
        return SubRedditDto.builder().name(subReddit.getName())
        .id(subReddit.getId())
        .postCount(subReddit.getPosts().size())
        .build();
    }

    private SubReddit mapToSubReddit(SubRedditDto subRedditDto){
        return SubReddit.builder().name("/r/"+subRedditDto.getName())
        .description(subRedditDto.getDescription())
        .user(authService.getCurrentUser())
        .createdDate(now()).build();
    }    


}
