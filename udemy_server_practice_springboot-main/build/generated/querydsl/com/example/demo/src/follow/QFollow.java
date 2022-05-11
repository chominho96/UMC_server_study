package com.example.demo.src.follow;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFollow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFollow extends EntityPathBase<Follow> {

    private static final long serialVersionUID = 137439722L;

    public static final QFollow follow = new QFollow("follow");

    public final com.example.demo.src.common.QBaseEntity _super = new com.example.demo.src.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> followeeIdx = createNumber("followeeIdx", Long.class);

    public final NumberPath<Long> followerIdx = createNumber("followerIdx", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<FollowStatus> status = createEnum("status", FollowStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QFollow(String variable) {
        super(Follow.class, forVariable(variable));
    }

    public QFollow(Path<? extends Follow> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFollow(PathMetadata metadata) {
        super(Follow.class, metadata);
    }

}

