package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalReply is a Querydsl query type for AnimalReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalReply extends EntityPathBase<AnimalReply> {

    private static final long serialVersionUID = 1212485032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalReply animalReply = new QAnimalReply("animalReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAnimal animal;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final NumberPath<Long> rno = createNumber("rno", Long.class);

    public final StringPath text = createString("text");

    public QAnimalReply(String variable) {
        this(AnimalReply.class, forVariable(variable), INITS);
    }

    public QAnimalReply(Path<? extends AnimalReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalReply(PathMetadata metadata, PathInits inits) {
        this(AnimalReply.class, metadata, inits);
    }

    public QAnimalReply(Class<? extends AnimalReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new QAnimal(forProperty("animal")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

