package com.example.project1.repository.missing;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;
import com.example.project1.entity.QMember;
import com.example.project1.entity.QMissing;
import com.example.project1.entity.QMissingimage;
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
public class MissingMemberReviewRepositoryImpl extends QuerydslRepositorySupport
        implements MissingMemberReviewRepository {

    public MissingMemberReviewRepositoryImpl() {
        super(Missing.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {

        // Q클래스
        QMissing missing = QMissing.missing;
        QMember member = QMember.member;
        QMissingimage missingimage = QMissingimage.missingimage;

        JPQLQuery<Missingimage> query = from(missingimage);
        query.leftJoin(missingimage.missing, missing);

        JPQLQuery<Tuple> tuple = query.select(missing, missingimage,
                JPAExpressions.select(member.mid).from(member).where(missing.member.eq(member)),
                JPAExpressions.select(member.email).from(member).where(missing.member.eq(member)),
                JPAExpressions.select(member.nickname).from(member).where(missing.member.eq(member)))
                .where(missingimage.inum.in(
                        JPAExpressions.select(missingimage.inum.min()).from(missingimage)
                                .groupBy(missingimage.missing)));

        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(missing.missno.gt(0L));

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(missing.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(missing.text.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(missing.member.nickname.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 정보
        // sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Missing> orderByExpression = new PathBuilder<>(Missing.class,
                    "missing");
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
    public List<Object[]> getRow(Long missno) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRow'");
    }

}
