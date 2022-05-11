package com.example.demo.src.user.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class GetUserPostsRes {

    private Long postIdx;
    private String postImgUrl;

    @QueryProjection
    public GetUserPostsRes(Long postIdx, String postImgUrl) {
        this.postIdx = postIdx;
        this.postImgUrl = postImgUrl;
    }
}
