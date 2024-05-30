package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalCart is a Querydsl query type for AnimalCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalCart extends EntityPathBase<AnimalCart> {

    private static final long serialVersionUID = 592851106L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalCart animalCart = new QAnimalCart("animalCart");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<AnimalItem, QAnimalItem> animalItems = this.<AnimalItem, QAnimalItem>createList("animalItems", AnimalItem.class, QAnimalItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> cartId = createNumber("cartId", Long.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public QAnimalCart(String variable) {
        this(AnimalCart.class, forVariable(variable), INITS);
    }

    public QAnimalCart(Path<? extends AnimalCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalCart(PathMetadata metadata, PathInits inits) {
        this(AnimalCart.class, metadata, inits);
    }

    public QAnimalCart(Class<? extends AnimalCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

