package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChart is a Querydsl query type for Chart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChart extends EntityPathBase<Chart> {

    private static final long serialVersionUID = 95157944L;

    public static final QChart chart = new QChart("chart");

    public final StringPath careAddr = createString("careAddr");

    public final StringPath careNm = createString("careNm");

    public final StringPath careTel = createString("careTel");

    public final NumberPath<Long> chId = createNumber("chId", Long.class);

    public final DatePath<java.time.LocalDate> happenDt = createDate("happenDt", java.time.LocalDate.class);

    public final StringPath kindCd = createString("kindCd");

    public final StringPath processState = createString("processState");

    public QChart(String variable) {
        super(Chart.class, forVariable(variable));
    }

    public QChart(Path<? extends Chart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChart(PathMetadata metadata) {
        super(Chart.class, metadata);
    }

}

