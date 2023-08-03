//package com.sparta.newpost.post.service;
//
//import com.sparta.newpost.comment.entity.Comment;
//import com.sparta.newpost.comment.repository.CommentRepository;
//import com.sparta.newpost.post.dto.PostRequestDto;
//import com.sparta.newpost.post.dto.PostResponseDto;
//import com.sparta.newpost.post.entity.Post;
//import com.sparta.newpost.post.repository.PostRepository;
//import com.sparta.newpost.security.UserDetailsImpl;
//import com.sparta.newpost.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//
//    public void createPost(PostRequestDto requestDto, UserDetailsImpl user) {
//
//        //유효한 토큰 인지
//        if(!user.isEnabled()){
//            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
//        }
//
//        User inUser = user.getUser();
//
//        Post post = new Post(requestDto.getTitle(), requestDto.getContent(),inUser);
//
//        postRepository.save(post);
//
//    }
//
//    public List<PostResponseDto> getPosts() {
//        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
//        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
//
//        for( Post post :postList){
//            PostResponseDto postResponseDto = new PostResponseDto(post,post.getCommentList());
//            postResponseDtoList.add(postResponseDto);
//        }
//
//        return postResponseDtoList;
//    }
//
//    public PostResponseDto getPost(Long id) {
//        Optional<Post> post = postRepository.findById(id);
//
//        if(!post.isPresent()){
//            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
//        }
//
//        return new PostResponseDto(post.get(),post.get().getCommentList());
//
//    }
//
//    @Transactional
//    public void chPost(Long id,PostRequestDto requestDto ,UserDetailsImpl user) {
//        Optional<Post> post = postRepository.findById(id);
//
//        if(!post.isPresent()){
//            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
//        }
//
//        if(!post.get().getUser().getUsername().equals(user.getUsername())){
//            throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
//        }
//
//        String title = requestDto.getTitle();
//        String content = requestDto.getContent();
//
//        post.get().update(title,content);
//
//    }
//
//    public void delPost(Long id, UserDetailsImpl user) {
//        Optional<Post> post = postRepository.findById(id);
//
//        if(!post.isPresent()){
//            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
//        }
//
//        if(!post.get().getUser().getUsername().equals(user.getUsername())){
//            throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
//        }
//
//        postRepository.delete(post.get());
//
//    }
//}
