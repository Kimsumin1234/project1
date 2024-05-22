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
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyCommentDto {
    private Long commentNo;

    private String text;

    // member
    private Long mid;
    private String email;
    private String nickname;

    // Reply
    private Long replyNo;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
