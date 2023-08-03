package com.sparta.newpost.comment.service;

import com.sparta.newpost.comment.dto.CommentRequestDto;
import com.sparta.newpost.comment.entity.Comment;
import com.sparta.newpost.comment.repository.CommentRepository;
import com.sparta.newpost.post.entity.Post;
import com.sparta.newpost.post.repository.PostRepository;
import com.sparta.newpost.security.UserDetailsImpl;
import com.sparta.newpost.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long id, CommentRequestDto requestDto, UserDetailsImpl user) {

        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()){
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        User inUser = user.getUser();

        Comment comment = new Comment(inUser, post.get(), requestDto.getComment());

        commentRepository.save(comment);

    }

    @Transactional
    public void chComment(Long id, CommentRequestDto requestDto, UserDetailsImpl user) {
        Optional<Comment> comment = commentRepository.findById(id);

        if(!comment.isPresent()){
            throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다.");
        }

        if(!comment.get().getUser().getUsername().equals(user.getUser().getUsername())){
            throw new IllegalArgumentException("해당 댓글의 작성자가 아닙니다.");
        }
        comment.get().update(requestDto.getComment());

    }

    public void delComment(Long id, UserDetailsImpl user) {
        Optional<Comment> comment = commentRepository.findById(id);

        if(!comment.isPresent()){
            throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다.");
        }

        if(!comment.get().getUser().getUsername().equals(user.getUser().getUsername())){
            throw new IllegalArgumentException("해당 댓글의 작성자가 아닙니다.");
        }

        commentRepository.delete(comment.get());
    }
}
