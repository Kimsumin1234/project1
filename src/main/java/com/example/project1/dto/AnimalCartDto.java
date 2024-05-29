package com.example.project1.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.project1.entity.AnimalItem;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class AnimalCartDto {

    private Long cartId;

    private int count; // // 카트에 담긴 상품 개수

    // 멤버
    private Long mid;
    private String email;
    private String nickname;

    // Animal
    @Builder.Default
    private List<AnimalItem> animalItems = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
