package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AnimalItemDto {

    private Long itemId;

    private int count;

    // 장바구니 번호
    private Long cartId;

    // Animal
    private Long sId;
    private String filename; // 섬네일이미지
    private String popfile; // 이미지

}
