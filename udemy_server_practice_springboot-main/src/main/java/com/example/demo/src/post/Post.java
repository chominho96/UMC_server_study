package com.example.demo.src.post;

import com.example.demo.src.comment.Comment;
import com.example.demo.src.common.BaseEntity;
import com.example.demo.src.user.model.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_idx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    User user;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post")
    private List<PostImg> postImgList;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

}
