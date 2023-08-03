package com.sparta.newpost.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchResponseDto {
    private String title;

    public PostSearchResponseDto(String title){
        this.title = title;
    }
}
