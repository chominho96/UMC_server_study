package com.example.demo.src.post;

import com.example.demo.src.user.model.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Likes {

    @GeneratedValue
    @Id
    @Column(name = "like_idx")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

}
