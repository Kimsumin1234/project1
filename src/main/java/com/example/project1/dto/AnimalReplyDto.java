package com.example.project1.dto;

import java.time.LocalDateTime;

import com.example.project1.entity.Animal;

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
public class AnimalReplyDto {

    private Long rno;

    private String text;

    // 멤버
    private Long mid;
    private String email;
    private String nickname;

    // Animal 글 번호
    private Long sid;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
