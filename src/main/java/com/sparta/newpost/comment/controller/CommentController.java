package com.sparta.newpost.comment.controller;

import com.sparta.newpost.comment.dto.CommentRequestDto;
import com.sparta.newpost.comment.service.CommentService;
import com.sparta.newpost.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/postNumber/{id}/comment")
    public void createComment(@PathVariable Long id , @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl user){
        commentService.createComment(id,requestDto,user);
    }

    @PutMapping("/comment/{id}")
    public void chComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl user){
        commentService.chComment(id, requestDto, user);
    }

    @DeleteMapping("/comment/{id}")
    public void delComment(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl user){
        commentService.delComment(id,user);
    }

}
