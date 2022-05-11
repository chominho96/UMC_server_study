package com.example.demo.src.follow;

import com.example.demo.src.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "follow_idx")
    private Long id;

    @Column(name = "follower_idx")
    private Long followerIdx;

    @Column(name = "followee_idx")
    private Long followeeIdx;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FollowStatus status;

}
