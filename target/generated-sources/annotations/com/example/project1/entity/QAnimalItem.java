package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalItem is a Querydsl query type for AnimalItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalItem extends EntityPathBase<AnimalItem> {

    private static final long serialVersionUID = 593047701L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalItem animalItem = new QAnimalItem("animalItem");

    public final QAnimal animal;

    public final QAnimalCart animalCart;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public QAnimalItem(String variable) {
        this(AnimalItem.class, forVariable(variable), INITS);
    }

    public QAnimalItem(Path<? extends AnimalItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalItem(PathMetadata metadata, PathInits inits) {
        this(AnimalItem.class, metadata, inits);
    }

    public QAnimalItem(Class<? extends AnimalItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new QAnimal(forProperty("animal")) : null;
        this.animalCart = inits.isInitialized("animalCart") ? new QAnimalCart(forProperty("animalCart"), inits.get("animalCart")) : null;
    }

}

