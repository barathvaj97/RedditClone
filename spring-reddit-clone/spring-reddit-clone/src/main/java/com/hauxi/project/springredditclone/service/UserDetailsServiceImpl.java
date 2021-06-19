package com.hauxi.project.springredditclone.service;

import java.util.Collection;
import java.util.Optional;

import com.hauxi.project.springredditclone.model.Users;
import com.hauxi.project.springredditclone.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

private final UserRepository userRepository;
   
   @Override
   @Transactional(readOnly = true)
   public UserDetails loadUserByUsername(String username)
   {
       Optional <Users> userOptional =userRepository.findByUsername(username);
       Users user =userOptional.
                    orElseThrow(()-> new UsernameNotFoundException( "No User "+ "Found with Username: "+ username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
   }

private Collection<? extends GrantedAuthority> getAuthorities(String role) {
    return singletonList(new SimpleGrantedAuthority(role));
    }   
}
