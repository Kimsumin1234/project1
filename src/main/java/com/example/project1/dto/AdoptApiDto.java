package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AdoptApiDto {

    private Long desertionNo; // 유기번호

    private String filename; // 썸네일이미지

    private String happenDt; // 접수일

    private String kind; // 품종

    private String color; // 색상

    private String age; // 나이

    private String weight; // 몸무게

    private String noticeNo; // 공고번호

    private String noticeSdt; // 공고시작일

    private String noticeEdt; // 공고종료일

    private String popfile; // 이미지

    private String procssState; // 상태

    private String sex; // 성별

    private String neuterYn; // 중성화여부

    private String specialMark; // 특징

    private String careNm; // 보호소 이름

    private String careTel; // 보호소 전화번호

    private String careAddr; // 보호 장소

    private String orgNm; // 관할기관

    private String chargeNm; // 담당자

    private String officetel; // 담당자연락처

}
