package com.example.project1.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String text;

    private Long replyCount;

    private Long viewCount;

    private Long heartCount;

    // member
    private Long mid;
    private String email;
    private String nickname;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    // 리뷰 이미지 리스트
    @Builder.Default // Builder 를 쓰면
    private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();
}
