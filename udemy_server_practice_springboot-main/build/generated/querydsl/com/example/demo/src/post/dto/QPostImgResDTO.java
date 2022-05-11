package com.example.demo.src.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.demo.src.post.dto.QPostImgResDTO is a Querydsl Projection type for PostImgResDTO
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QPostImgResDTO extends ConstructorExpression<PostImgResDTO> {

    private static final long serialVersionUID = 1483108489L;

    public QPostImgResDTO(com.querydsl.core.types.Expression<Long> postImgIdx, com.querydsl.core.types.Expression<String> imgUrl) {
        super(PostImgResDTO.class, new Class<?>[]{long.class, String.class}, postImgIdx, imgUrl);
    }

}

