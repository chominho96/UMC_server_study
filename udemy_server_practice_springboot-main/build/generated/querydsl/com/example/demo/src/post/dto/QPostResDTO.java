package com.example.demo.src.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.demo.src.post.dto.QPostResDTO is a Querydsl Projection type for PostResDTO
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QPostResDTO extends ConstructorExpression<PostResDTO> {

    private static final long serialVersionUID = -1953547080L;

    public QPostResDTO(com.querydsl.core.types.Expression<Long> postIdx, com.querydsl.core.types.Expression<Long> userIdx, com.querydsl.core.types.Expression<String> nickName, com.querydsl.core.types.Expression<String> profileImgUrl, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> postLikeCount, com.querydsl.core.types.Expression<Long> commentCount, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt) {
        super(PostResDTO.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, long.class, long.class, java.time.LocalDateTime.class}, postIdx, userIdx, nickName, profileImgUrl, content, postLikeCount, commentCount, updatedAt);
    }

}

