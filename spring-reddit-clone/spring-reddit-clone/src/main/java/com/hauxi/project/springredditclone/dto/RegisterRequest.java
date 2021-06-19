package com.hauxi.project.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    private String username;
    private String email;
    private String password;
}
