package com.example.project1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.project1.entity.Heart;
import com.example.project1.entity.QHeart;
import com.example.project1.entity.QMember;
import com.example.project1.entity.QReview;
import com.example.project1.entity.QReviewImage;
import com.example.project1.entity.QReviewReply;
import com.example.project1.entity.QReviewReplyComment;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReviewMemberReviewReplyReviewReplyCommentHeartRepositoryImpl extends QuerydslRepositorySupport
        implements ReviewMemberReviewReplyReviewReplyCommentHeartRepository {

    public ReviewMemberReviewReplyReviewReplyCommentHeartRepositoryImpl() {
        super(Review.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("================== queryDsl List ====================");
        log.info("review + reply + member + reviewimage join");

        // Q 클래스 사용
        QReview review = QReview.review;
        QMember member = QMember.member;
        QReviewReply reply = QReviewReply.reviewReply;
        QReviewImage reviewImage = QReviewImage.reviewImage;
        QHeart heart = QHeart.heart;

        // @Query("select r, m from review r left join r.writer m") // findby*
        JPQLQuery<ReviewImage> query = from(reviewImage);
        query.leftJoin(reviewImage.review, review);
        // subquery => JPAExpressions // JPAExpressions.select() 메서드는 서브쿼리를 생성합니다.
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.replyNo.count().as("replycnt"))
                .from(reply)
                .where(reply.review.eq(review))
                .groupBy(reply.review);

        JPQLQuery<Long> heartCount = JPAExpressions.select(heart.hno.count().as("heartCount"))
                .from(heart)
                .where(heart.review.eq(review))
                .groupBy(heart.review);

        JPQLQuery<Tuple> tuple = query.select(review, reviewImage,
                JPAExpressions.select(member.mid).from(member).where(review.writer.eq(member)),
                JPAExpressions.select(member.email).from(member).where(review.writer.eq(member)),
                JPAExpressions.select(member.nickname).from(member).where(review.writer.eq(member)),
                replyCount, heartCount)
                .where(reviewImage.inum.in(
                        JPAExpressions.select(reviewImage.inum.min()).from(reviewImage).groupBy(reviewImage.review)));

        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.rno.gt(0L));

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(review.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(review.text.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(review.writer.nickname.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 정보
        // sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Review> orderByExpression = new PathBuilder<>(Review.class,
                    "review");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        // 페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        // 전체 개수
        long count = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }

    @Override
    public List<Object[]> getRow(Long rno) {
        log.info("get Row SearchBoardRepository");

        // Q 클래스 사용
        QReview review = QReview.review;
        QMember member = QMember.member;
        QReviewReply reply = QReviewReply.reviewReply;
        QReviewImage reviewImage = QReviewImage.reviewImage;
        QReviewReplyComment replyComment = QReviewReplyComment.reviewReplyComment;
        QHeart heart = QHeart.heart;

        // @Query("select b, m from board b left join b.writer m") // findby*
        JPQLQuery<ReviewImage> query = from(reviewImage);
        query.leftJoin(reviewImage.review, review);
        // query.leftJoin(reviewImage.review, replyComment.reply.review);
        // subquery => JPAExpressions // JPAExpressions.select() 메서드는 서브쿼리를 생성합니다.
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.replyNo.count().as("replycnt"))
                .from(reply)
                .where(reply.review.eq(review))
                .groupBy(reply.review);
        JPQLQuery<Long> heartCount = JPAExpressions.select(heart.hno.count().as("heartCount"))
                .from(heart)
                .where(heart.review.eq(review))
                .groupBy(heart.review);
        // JPAExpressions.select(replyComment.text).from(replyComment)
        // .where(reply.replyNo.eq(replyComment.reply.replyNo)
        JPQLQuery<Tuple> tuple = query.select(review, reviewImage,
                JPAExpressions.select(member.mid).from(member).where(review.writer.eq(member)),
                JPAExpressions.select(member.email).from(member).where(review.writer.eq(member)),
                JPAExpressions.select(member.nickname).from(member).where(review.writer.eq(member)),
                replyCount, heartCount)
                .where(reviewImage.review.rno.eq(rno))
                .orderBy(reviewImage.inum.desc());

        List<Tuple> result = tuple.fetch();

        return result.stream().map(t -> t.toArray()).collect(Collectors.toList()); // Tuple -> Array
    }
}
