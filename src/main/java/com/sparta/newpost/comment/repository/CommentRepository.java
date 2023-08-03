package com.sparta.newpost.comment.repository;

import com.sparta.newpost.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findAllByPost_id(Long id);
}
