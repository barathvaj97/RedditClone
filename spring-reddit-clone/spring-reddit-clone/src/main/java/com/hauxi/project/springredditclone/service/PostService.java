package com.hauxi.project.springredditclone.service;

import java.util.List;

import com.hauxi.project.springredditclone.dto.PostRequest;
import com.hauxi.project.springredditclone.dto.PostResponse;
import com.hauxi.project.springredditclone.exception.PostNotFoundException;
import com.hauxi.project.springredditclone.exception.SubRedditNotFoundException;
import com.hauxi.project.springredditclone.mapper.PostMapper;
import com.hauxi.project.springredditclone.model.Post;
import com.hauxi.project.springredditclone.model.SubReddit;
import com.hauxi.project.springredditclone.model.Users;
import com.hauxi.project.springredditclone.repository.PostRepository;
import com.hauxi.project.springredditclone.repository.SubRedditRepository;
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
public class PostService {
    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    

    public void save(PostRequest postRequest){
        SubReddit subReddit=subRedditRepository.findByName(postRequest.getSubRedditName())
        .orElseThrow(()->new SubRedditNotFoundException(postRequest.getSubRedditName()));

        postRepository.save(postMapper.map(postRequest, subReddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post =postRepository.findById(id)
            .orElseThrow(()-> new PostNotFoundException(id.toString()));
        
        return postMapper.mapToDto(post); 
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
        .stream()
        .map(postMapper::mapToDto)
        .collect(toList());
    }    

    @Transactional (readOnly = true)
    public List<PostResponse> getPostsBySubReddit(Long subRedditId){
        SubReddit subReddit =subRedditRepository.findById((subRedditId))
        .orElseThrow(()->new SubRedditNotFoundException(subRedditId.toString()));
        List<Post> posts=postRepository.findAllBySubReddit(subReddit);

        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional (readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        Users user = userRepository.findByUsername(username)
        .orElseThrow(()->new UsernameNotFoundException(username));
        return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());   
    }
}
