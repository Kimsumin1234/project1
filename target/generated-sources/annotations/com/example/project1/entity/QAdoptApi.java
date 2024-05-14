package com.example.project1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdoptApi is a Querydsl query type for AdoptApi
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdoptApi extends EntityPathBase<AdoptApi> {

    private static final long serialVersionUID = 2125220656L;

    public static final QAdoptApi adoptApi = new QAdoptApi("adoptApi");

    public final StringPath age = createString("age");

    public final StringPath careAddr = createString("careAddr");

    public final StringPath careNm = createString("careNm");

    public final StringPath careTel = createString("careTel");

    public final StringPath colorCd = createString("colorCd");

    public final StringPath filename = createString("filename");

    public final NumberPath<Integer> happenDt = createNumber("happenDt", Integer.class);

    public final StringPath kindCd = createString("kindCd");

    public final StringPath neuterYn = createString("neuterYn");

    public final StringPath noticeEdt = createString("noticeEdt");

    public final NumberPath<Integer> noticeNo = createNumber("noticeNo", Integer.class);

    public final NumberPath<Integer> noticeSdt = createNumber("noticeSdt", Integer.class);

    public final StringPath officetel = createString("officetel");

    public final StringPath orgNm = createString("orgNm");

    public final StringPath orgNmC = createString("orgNmC");

    public final StringPath popfile = createString("popfile");

    public final StringPath procssState = createString("procssState");

    public final StringPath sexCd = createString("sexCd");

    public final NumberPath<Long> sId = createNumber("sId", Long.class);

    public final StringPath specialMark = createString("specialMark");

    public final StringPath weight = createString("weight");

    public QAdoptApi(String variable) {
        super(AdoptApi.class, forVariable(variable));
    }

    public QAdoptApi(Path<? extends AdoptApi> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdoptApi(PathMetadata metadata) {
        super(AdoptApi.class, metadata);
    }

}

