package com.example.demo.src.post.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PostResDTO {

    private Long postIdx;
    private Long userIdx;
    private String nickName;
    private String profileImgUrl;
    private String content;
    private Long postLikeCount;
    private Long commentCount;
    private LocalDateTime updatedAt;
    private boolean likeOrNot;
    // if Like, like_idx
    // if not like, null
    private List<PostImgResDTO> imgList;

    @QueryProjection
    public PostResDTO(Long postIdx, Long userIdx, String nickName, String profileImgUrl, String content,
                      Long postLikeCount, Long commentCount, LocalDateTime updatedAt) {
        this.postIdx = postIdx;
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.content = content;
        this.postLikeCount = postLikeCount;
        this.commentCount = commentCount;
        this.updatedAt = updatedAt;
        this.imgList = new ArrayList<>();
    }
}
