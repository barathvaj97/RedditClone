package com.hauxi.project.springredditclone.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;        //Doubt
import static javax.persistence.FetchType.LAZY;                 //Doubt

import java.time.Instant;

@Data                   //for getters and setters generate
@Entity                 //JPA annotation
@Builder                //uses builder design pattern for generating objects
@AllArgsConstructor     //generates constructor for the class
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @NotBlank(message="Post name cannot be empty")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private Users user;
    private Instant createDate;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id",referencedColumnName = "id")
    private SubReddit subReddit;

}
