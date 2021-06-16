package com.hauxi.project.springredditclone.controller;

import java.util.List;

import javax.validation.Valid;

import com.hauxi.project.springredditclone.dto.SubRedditDto;
import com.hauxi.project.springredditclone.service.SubRedditService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {

    private final SubRedditService subRedditService;

    @GetMapping
    public List<SubRedditDto> getAllReddits(){
        return subRedditService.getAll();
    }

    @GetMapping("/{id}")
    public SubRedditDto getSubReddit(@PathVariable Long id){
        return subRedditService.getSubReddit(id);
    }

    @PostMapping
    public SubRedditDto create(@RequestBody @Valid SubRedditDto subRedditDto){
        return subRedditService.save(subRedditDto);
    }

}
