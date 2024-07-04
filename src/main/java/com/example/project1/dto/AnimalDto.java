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
public class AnimalDto {

    private Long sId; // 유기번호

    private String filename; // 섬네일이미지

    private String happenDt; // 접수일

    private String kindCd; // 품종

    private String colorCd; // 색상

    private String age; // 나이

    private String weight; // 몸무게

    private String noticeNo; // 공고번호

    private int noticeSdt; // 공고시작일

    private String noticeEdt; // 공고종료일

    private String popfile; // 이미지

    private String procssState; // 상태

    private String sexCd; // 성별

    private String neuterYn; // 중성화여부

    private String specialMark; // 특징

    private String careNm; // 보호소 이름

    private String careTel; // 보호소 전화번호

    private String careAddr; // 보호 장소

    private String orgNm; // 관할기관(시,도)

    private String orgNmc; // 관할기관(시,군,구)

    private String officetel; // 관할기관연락처

    private Long reviewCnt; // 리뷰 개수

    // 하트 개수
    private Long animalCnt;

}