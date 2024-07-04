package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMissingReply is a Querydsl query type for MissingReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMissingReply extends EntityPathBase<MissingReply> {

    private static final long serialVersionUID = 72147210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMissingReply missingReply = new QMissingReply("missingReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final QMissing missing;

    public final NumberPath<Long> missrno = createNumber("missrno", Long.class);

    public final StringPath text = createString("text");

    public QMissingReply(String variable) {
        this(MissingReply.class, forVariable(variable), INITS);
    }

    public QMissingReply(Path<? extends MissingReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMissingReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMissingReply(PathMetadata metadata, PathInits inits) {
        this(MissingReply.class, metadata, inits);
    }

    public QMissingReply(Class<? extends MissingReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.missing = inits.isInitialized("missing") ? new QMissing(forProperty("missing"), inits.get("missing")) : null;
    }

}

