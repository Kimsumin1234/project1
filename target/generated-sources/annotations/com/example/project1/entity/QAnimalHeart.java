package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalHeart is a Querydsl query type for AnimalHeart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalHeart extends EntityPathBase<AnimalHeart> {

    private static final long serialVersionUID = 1203235588L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalHeart animalHeart = new QAnimalHeart("animalHeart");

    public final QAnimal animal;

    public final NumberPath<Long> hid = createNumber("hid", Long.class);

    public final QMember member;

    public QAnimalHeart(String variable) {
        this(AnimalHeart.class, forVariable(variable), INITS);
    }

    public QAnimalHeart(Path<? extends AnimalHeart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalHeart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalHeart(PathMetadata metadata, PathInits inits) {
        this(AnimalHeart.class, metadata, inits);
    }

    public QAnimalHeart(Class<? extends AnimalHeart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new QAnimal(forProperty("animal")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

