package com.example.project1.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Animal;
import com.example.project1.repository.AdoptApiRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdoptApiServiceImpl implements AdoptApiService {

    private final AdoptApiRepository adoptApiRepository;

    @Override
    public PageResultDto<AnimalDto, Animal> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("sId").descending());

        Page<Animal> result = adoptApiRepository
                .findAll(adoptApiRepository.makePredicate(requestDto.getType(), requestDto.getKeyword()), pageable);
        Function<Animal, AnimalDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    // @Override
    // public PageResultDto<AdoptApiDto, AdoptApi> getList(PageRequestDto
    // requestDto) {
    // Pageable pageable = requestDto.getPageable(Sort.by("sId").descending());

    // BooleanBuilder builder = getSearch(requestDto);
    // Page<AdoptApi> result = adoptApiRepository.findAll(builder, pageable);

    // Function<AdoptApi, AdoptApiDto> fn = (entity -> entityToDto(entity));
    // return new PageResultDto<AdoptApiDto, AdoptApi>(result, fn);

    // }

    // 검색기능
    // private BooleanBuilder getSearch(PageRequestDto requestDto) {
    // BooleanBuilder builder = new BooleanBuilder();

    // // Q 클래스 QGuestBook 사용
    // QAdoptApi guestBook = QAdoptApi.adoptApi;

    // // type, keyword 가져오기
    // String type = requestDto.getType();
    // String keyword = requestDto.getKeyword();

    // // WHERE sId>0 AND title LIKE '%Title%' OR content LIKE '%content%'
    // // sId > 0 (gt : graterthan)
    // // sId > 0 쓰는 이유 : 성능이 좋아짐(빨라짐)
    // builder.and(guestBook.sId.gt(0L));

    // if (type == null || type.trim().length() == 0) {
    // return builder;
    // }

    // // 검색 타입이 있는 경우
    // // tc, tcw 때문에 contains 사용
    // BooleanBuilder conditionBuilder = new BooleanBuilder();
    // if (type.contains("t")) {
    // // OR content LIKE '%content%'
    // conditionBuilder.or(guestBook.orgNm.contains(keyword));
    // }
    // if (type.contains("c")) {
    // // OR content LIKE '%content%'
    // conditionBuilder.or(guestBook.orgNmC.contains(keyword));
    // }

    // builder.and(conditionBuilder);

    // return builder;
    // }

}
