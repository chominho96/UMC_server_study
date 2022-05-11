package com.example.demo.src.post.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImgResDTO {

    private Long postImgIdx;
    private String imgUrl;

    @QueryProjection
    public PostImgResDTO(Long postImgIdx, String imgUrl) {
        this.postImgIdx = postImgIdx;
        this.imgUrl = imgUrl;
    }
}
