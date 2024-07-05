package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMissingimage is a Querydsl query type for Missingimage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMissingimage extends EntityPathBase<Missingimage> {

    private static final long serialVersionUID = 93611931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMissingimage missingimage = new QMissingimage("missingimage");

    public final StringPath imagename = createString("imagename");

    public final NumberPath<Long> inum = createNumber("inum", Long.class);

    public final QMissing missing;

    public final StringPath path = createString("path");

    public final StringPath uuid = createString("uuid");

    public QMissingimage(String variable) {
        this(Missingimage.class, forVariable(variable), INITS);
    }

    public QMissingimage(Path<? extends Missingimage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMissingimage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMissingimage(PathMetadata metadata, PathInits inits) {
        this(Missingimage.class, metadata, inits);
    }

    public QMissingimage(Class<? extends Missingimage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.missing = inits.isInitialized("missing") ? new QMissing(forProperty("missing"), inits.get("missing")) : null;
    }

}

