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
public class AnimalHeartDto {

    private Long hid;

    private Long mid;
    private String email;

    private Long sid;

    private int animalHeartCount;

}
