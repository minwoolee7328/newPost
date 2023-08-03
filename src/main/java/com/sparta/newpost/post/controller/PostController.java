package com.sparta.newpost.post.controller;

import com.sparta.newpost.post.dto.PostRequestDto;
import com.sparta.newpost.post.dto.PostResponseDto;
import com.sparta.newpost.post.dto.PostSearchDto;
import com.sparta.newpost.post.dto.PostSearchResponseDto;
import com.sparta.newpost.post.entity.Post;
import com.sparta.newpost.post.postServiceImpl.PostServiceImpl;
import com.sparta.newpost.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    public final PostServiceImpl postServiceImpl;

    @PostMapping("/post")
    public void createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl user){
        postServiceImpl.createPost(requestDto,user);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
            ){
        return postServiceImpl.getPosts(page-1, size, sortBy, isAsc);
    }

    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postServiceImpl.getPost(id);
    }

    @PutMapping("/post/{id}")
    public void chPost(@PathVariable Long id,@RequestBody PostRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl user){
        postServiceImpl.chPost(id,requestDto, user);
    }

    @DeleteMapping("/post/{id}")
    public void delPost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl user){
        postServiceImpl.delPost(id,user);
    }

    @PostMapping("/post/search")
    public List<PostSearchResponseDto> getPostSearch(
            @RequestBody PostSearchDto searchDto,
            Pageable pageable
            ){
      return postServiceImpl.getPostSearch(searchDto, pageable);
    }
}
