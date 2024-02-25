package com.sini.mysns.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImage is a Querydsl query type for PostImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostImage extends EntityPathBase<PostImage> {

    private static final long serialVersionUID = 1860668389L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImage postImage = new QPostImage("postImage");

    public final com.sini.mysns.domain.QBaseTimeEntity _super = new com.sini.mysns.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QPost post;

    public final NumberPath<Long> postImageId = createNumber("postImageId", Long.class);

    public final NumberPath<Integer> postImageOrder = createNumber("postImageOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final StringPath url = createString("url");

    public QPostImage(String variable) {
        this(PostImage.class, forVariable(variable), INITS);
    }

    public QPostImage(Path<? extends PostImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImage(PathMetadata metadata, PathInits inits) {
        this(PostImage.class, metadata, inits);
    }

    public QPostImage(Class<? extends PostImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

