package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewReplyComment is a Querydsl query type for ReviewReplyComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewReplyComment extends EntityPathBase<ReviewReplyComment> {

    private static final long serialVersionUID = 315656947L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewReplyComment reviewReplyComment = new QReviewReplyComment("reviewReplyComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> commentNo = createNumber("commentNo", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QReviewReply reply;

    public final QMember replyer;

    public final QReview review;

    public final StringPath text = createString("text");

    public QReviewReplyComment(String variable) {
        this(ReviewReplyComment.class, forVariable(variable), INITS);
    }

    public QReviewReplyComment(Path<? extends ReviewReplyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewReplyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewReplyComment(PathMetadata metadata, PathInits inits) {
        this(ReviewReplyComment.class, metadata, inits);
    }

    public QReviewReplyComment(Class<? extends ReviewReplyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reply = inits.isInitialized("reply") ? new QReviewReply(forProperty("reply"), inits.get("reply")) : null;
        this.replyer = inits.isInitialized("replyer") ? new QMember(forProperty("replyer")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

