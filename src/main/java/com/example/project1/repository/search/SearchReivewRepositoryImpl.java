// package com.example.project1.repository.search;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import
// org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

// import com.example.project1.entity.QMember;
// import com.example.project1.entity.QReview;
// import com.example.project1.entity.QReviewReply;
// import com.example.project1.entity.Review;
// import com.querydsl.core.BooleanBuilder;
// import com.querydsl.core.Tuple;
// import com.querydsl.core.types.Order;
// import com.querydsl.core.types.OrderSpecifier;
// import com.querydsl.core.types.dsl.PathBuilder;
// import com.querydsl.jpa.JPAExpressions;
// import com.querydsl.jpa.JPQLQuery;

// import lombok.extern.log4j.Log4j2;

// @Log4j2
// public class SearchReivewRepositoryImpl extends QuerydslRepositorySupport
// implements SearchReviewRepository {

// public SearchReivewRepositoryImpl() {
// super(Review.class);
// }

// @Override
// public Page<Object[]> list(String type, String keyword, Pageable pageable) {
// log.info("review + reply + member join");

// // Q 클래스 사용
// QReview review = QReview.review;
// QMember member = QMember.member;
// QReviewReply reply = QReviewReply.reviewReply;

// // @Query("select r, m from review r left join r.writer m") // findby*
// JPQLQuery<Review> query = from(review);
// query.leftJoin(review.writer, member);

// // subquery => JPAExpressions // JPAExpressions.select() 메서드는 서브쿼리를 생성합니다.
// JPQLQuery<Long> replyCount =
// JPAExpressions.select(reply.replyNo.count().as("replycnt"))
// .from(reply)
// .where(reply.review.eq(review))
// .groupBy(reply.review);

// JPQLQuery<Tuple> tuple = query.select(review, member, replyCount);

// // 검색
// BooleanBuilder builder = new BooleanBuilder();
// builder.and(review.rno.gt(0L));

// BooleanBuilder conditionBuilder = new BooleanBuilder();
// if (type.contains("t")) {
// conditionBuilder.or(review.title.contains(keyword));
// }
// if (type.contains("c")) {
// conditionBuilder.or(review.text.contains(keyword));
// }
// if (type.contains("w")) {
// conditionBuilder.or(member.nickname.contains(keyword));
// }
// builder.and(conditionBuilder);
// tuple.where(builder);

// // type, keyword

// // 페이지 나누기 정보
// // sort 지정
// Sort sort = pageable.getSort();
// sort.stream().forEach(order -> {
// Order direction = order.isAscending() ? Order.ASC : Order.DESC;
// String prop = order.getProperty();

// PathBuilder<Review> orderByExpression = new PathBuilder<>(Review.class,
// "review");
// tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
// });
// // 페이지 처리
// tuple.offset(pageable.getOffset());
// tuple.limit(pageable.getPageSize());

// List<Tuple> result = tuple.fetch();
// // 전체 개수
// long count = tuple.fetchCount();

// return new PageImpl<>(result.stream().map(t ->
// t.toArray()).collect(Collectors.toList()), pageable, count);
// }

// // @Override
// // public Object[] getRow(Long rno) {
// // log.info("get Row SearchBoardRepository");

// // // Q 클래스 사용
// // QReview review = QReview.review;
// // QMember member = QMember.member;
// // QReviewReply reply = QReviewReply.reviewReply;

// // // @Query("select b, m from board b left join b.writer m") // findby*
// // JPQLQuery<Review> query = from(review);
// // query.leftJoin(review.writer, member);
// // query.where(review.rno.eq(rno));

// // // subquery => JPAEXpressions
// // JPQLQuery<Long> replyCount =
// // JPAExpressions.select(reply.replyNo.count().as("replycnt"))
// // .from(reply)
// // .where(reply.review.eq(review))
// // .groupBy(reply.review);

// // JPQLQuery<Tuple> tuple = query.select(review, member, replyCount);
// // Tuple result = tuple.fetch().get(0);

// // return result.toArray(); // Tuple -> Array
// // }

// }
