package com.example.demo.src.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.demo.src.post.dto.QPostDTO is a Querydsl Projection type for PostDTO
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QPostDTO extends ConstructorExpression<PostDTO> {

    private static final long serialVersionUID = 671752422L;

    public QPostDTO(com.querydsl.core.types.Expression<Long> postIdx, com.querydsl.core.types.Expression<Long> userIdx, com.querydsl.core.types.Expression<String> nickName, com.querydsl.core.types.Expression<com.example.demo.src.post.PostStatus> status, com.querydsl.core.types.Expression<String> content) {
        super(PostDTO.class, new Class<?>[]{long.class, long.class, String.class, com.example.demo.src.post.PostStatus.class, String.class}, postIdx, userIdx, nickName, status, content);
    }

}

