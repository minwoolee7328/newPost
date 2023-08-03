package com.sparta.newpost.comment.entity;

import com.sparta.newpost.post.entity.Post;
import com.sparta.newpost.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글
    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(User user, Post post, String comment){
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    @Transactional
    public void update(String comment){
        this.comment = comment;
    }

}
