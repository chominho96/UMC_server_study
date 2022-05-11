package com.example.demo.src.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImg is a Querydsl query type for PostImg
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostImg extends EntityPathBase<PostImg> {

    private static final long serialVersionUID = -94905639L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImg postImg = new QPostImg("postImg");

    public final com.example.demo.src.common.QBaseEntity _super = new com.example.demo.src.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath imgUrl = createString("imgUrl");

    public final QPost post;

    public final NumberPath<Long> postImgUrlIdx = createNumber("postImgUrlIdx", Long.class);

    public final EnumPath<PostStatus> status = createEnum("status", PostStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QPostImg(String variable) {
        this(PostImg.class, forVariable(variable), INITS);
    }

    public QPostImg(Path<? extends PostImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImg(PathMetadata metadata, PathInits inits) {
        this(PostImg.class, metadata, inits);
    }

    public QPostImg(Class<? extends PostImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

