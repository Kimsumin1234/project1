package com.example.project1.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long rno;

    private String title;

    private String text;

    // member
    private Long mid;
    private String email;
    private String nickname;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
