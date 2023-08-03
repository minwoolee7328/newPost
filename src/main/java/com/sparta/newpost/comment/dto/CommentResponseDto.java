package com.sparta.newpost.comment.dto;

import com.sparta.newpost.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private String comment;

    public CommentResponseDto(Comment comment){
        this.comment = comment.getComment();
    }
}
