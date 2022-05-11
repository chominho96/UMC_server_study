package com.example.demo.src.user.model;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.demo.src.user.model.QGetUserPostsRes is a Querydsl Projection type for GetUserPostsRes
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QGetUserPostsRes extends ConstructorExpression<GetUserPostsRes> {

    private static final long serialVersionUID = -1260748630L;

    public QGetUserPostsRes(com.querydsl.core.types.Expression<Long> postIdx, com.querydsl.core.types.Expression<String> postImgUrl) {
        super(GetUserPostsRes.class, new Class<?>[]{long.class, String.class}, postIdx, postImgUrl);
    }

}

