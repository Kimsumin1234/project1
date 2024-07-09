package com.example.project1.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@Entity
public class Chart {

    @Id
    private Long chId; // 유기번호

    private LocalDate happenDt; // 접수일

    private String processState; // 상태

    private String kindCd; // 품종

    private String careNm; // 보호소 이름

    private String careAddr; // 보호 장소

    private String careTel; // 보호소 전화번호

}
