package com.example.demo.src.post.dto;

import com.example.demo.src.post.PostStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long postIdx;
    private Long userIdx;
    private String nickName;
    private PostStatus status;
    private String content;

    @QueryProjection
    public PostDTO(Long postIdx, Long userIdx, String nickName, PostStatus status, String content) {
        this.postIdx = postIdx;
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.status = status;
        this.content = content;
    }
}
