package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewReply is a Querydsl query type for ReviewReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewReply extends EntityPathBase<ReviewReply> {

    private static final long serialVersionUID = 1664294348L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewReply reviewReply = new QReviewReply("reviewReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<ReviewReplyComment, QReviewReplyComment> replyComment = this.<ReviewReplyComment, QReviewReplyComment>createList("replyComment", ReviewReplyComment.class, QReviewReplyComment.class, PathInits.DIRECT2);

    public final QMember replyer;

    public final NumberPath<Long> replyNo = createNumber("replyNo", Long.class);

    public final QReview review;

    public final StringPath text = createString("text");

    public QReviewReply(String variable) {
        this(ReviewReply.class, forVariable(variable), INITS);
    }

    public QReviewReply(Path<? extends ReviewReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewReply(PathMetadata metadata, PathInits inits) {
        this(ReviewReply.class, metadata, inits);
    }

    public QReviewReply(Class<? extends ReviewReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.replyer = inits.isInitialized("replyer") ? new QMember(forProperty("replyer")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

