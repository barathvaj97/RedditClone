package com.hauxi.project.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditDto {
    private long id;
    private String name;
    private String description;
    private Integer postCount;
}
