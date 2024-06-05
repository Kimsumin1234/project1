package com.example.project1.repository.animal;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.project1.entity.Animal;
import com.example.project1.entity.QAnimal;
import com.example.project1.entity.QAnimalHeart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AnimalAnimalHeartRepositoryImpl extends QuerydslRepositorySupport
        implements AnimalAnimalHeartRepository {

    public AnimalAnimalHeartRepositoryImpl() {
        super(Animal.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("================== queryDsl List ====================");

        // Q 클래스 사용
        QAnimal animal = QAnimal.animal;
        QAnimalHeart animalHeart = QAnimalHeart.animalHeart;

        JPQLQuery<Animal> query = from(animal);
        // query.leftJoin(animalHeart.animal);

        // 좋아요 개수 만큼 sId가 만들어짐
        JPQLQuery<Long> animalCnt = JPAExpressions.select(animalHeart.animal.sId.count().as("animalCnt"))
                .from(animalHeart)
                .where(animalHeart.animal.eq(animal))
                .groupBy(animalHeart.animal);

        JPQLQuery<Tuple> tuple = query.select(animal, animalCnt);

        BooleanBuilder builder = new BooleanBuilder();

        // id > 0
        builder.and(animal.sId.gt(0L));

        // 검색 타입이 있는 경우
        if (type.equals("oNm")) {
            builder.and(animal.orgNm.contains(keyword));
        } else if (type.equals("oNmc")) {
            builder.and(animal.orgNmc.contains(keyword));
        }

        tuple.where(builder);

        // 페이지 나누기 정보
        // sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Animal> orderByExpression = new PathBuilder<>(Animal.class,
                    "animal");
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

}
