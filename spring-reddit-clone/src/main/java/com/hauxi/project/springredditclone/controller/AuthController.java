package com.hauxi.project.springredditclone.controller;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import com.hauxi.project.springredditclone.dto.AuthenticationResponse;
import com.hauxi.project.springredditclone.dto.LoginRequest;
import com.hauxi.project.springredditclone.dto.RegisterRequest;
import com.hauxi.project.springredditclone.service.AuthService;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity <String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity  <> ("User Registration Successful", OK);
    }
    
    @GetMapping("accountVerification/{token}")
        public ResponseEntity<String> verifyAccount(@PathVariable String token) {
            authService.verifyAccount(token);
            return new ResponseEntity<>("Account Activated Successully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest LoginRequest){
        return authService.login(LoginRequest);
    }




}