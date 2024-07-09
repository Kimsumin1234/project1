package com.example.project1.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {
    private int page;
    private int size;

    private String type;
    private String keyword;
    private String sorting;

    public PageRequestDto() {
        this.page = 1;
        this.size = 12;
        this.type = "";
        this.keyword = "";
        this.sorting = "r";
        // 여기서 초기값 안주면 list 들어갈 때 list?page=1&type=&keyword= 이런식으로 주면 됨
    }

    // 스프링에서 페이지 나누기 정보 저장객체 => Pageable
    // 1페이지 -> 0 부터 시작
    public Pageable getPageable(Sort sort) {
        // Pageable 객체를 생성하기 위함
        return PageRequest.of(page - 1, size, sort);
    }
}
