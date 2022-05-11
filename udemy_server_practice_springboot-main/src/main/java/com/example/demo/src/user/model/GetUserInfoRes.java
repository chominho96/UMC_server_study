package com.example.demo.src.user.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoRes {

    private String nickName;
    private String name;
    private String profileImgUrl;
    private String website;
    private String introduction;
    private int followerCount;
    private int followingCount;
    private int postCount;

}
