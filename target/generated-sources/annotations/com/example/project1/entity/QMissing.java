package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMissing is a Querydsl query type for Missing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMissing extends EntityPathBase<Missing> {

    private static final long serialVersionUID = 1582848448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMissing missing = new QMissing("missing");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final ListPath<Missingimage, QMissingimage> missingImages = this.<Missingimage, QMissingimage>createList("missingImages", Missingimage.class, QMissingimage.class, PathInits.DIRECT2);

    public final NumberPath<Long> missno = createNumber("missno", Long.class);

    public final StringPath text = createString("text");

    public final StringPath title = createString("title");

    public QMissing(String variable) {
        this(Missing.class, forVariable(variable), INITS);
    }

    public QMissing(Path<? extends Missing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMissing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMissing(PathMetadata metadata, PathInits inits) {
        this(Missing.class, metadata, inits);
    }

    public QMissing(Class<? extends Missing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

