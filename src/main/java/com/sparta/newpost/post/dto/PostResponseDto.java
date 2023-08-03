package com.sparta.newpost.post.dto;

import com.sparta.newpost.comment.dto.CommentResponseDto;
import com.sparta.newpost.comment.entity.Comment;
import com.sparta.newpost.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();
//   private List<Comment> commentList = new ArrayList<>();

    public PostResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostResponseDto(Post post, List<Comment> commentList){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        sortCommentList(commentList);
    }

    public void sortCommentList(List<Comment> commentList){
        for (Comment comment: commentList ) {
            this.commentList.add(new CommentResponseDto(comment));
        }
    }

//    public void sortCommentList(List<Comment> commentList){
//        for (Comment comment: commentList ) {
//            this.commentList.add(comment);
//        }
//    }

}
