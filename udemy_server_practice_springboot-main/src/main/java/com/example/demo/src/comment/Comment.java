package com.example.demo.src.comment;

import com.example.demo.src.common.BaseEntity;
import com.example.demo.src.post.Post;
import com.example.demo.src.user.model.User;
import lombok.Getter;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_idx")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "parent_comment_idx")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommentStatus status;


}
