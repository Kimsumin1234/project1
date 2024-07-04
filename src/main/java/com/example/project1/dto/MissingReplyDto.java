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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MissingReplyDto {
    private Long missrno;

    private String text;

    // member
    private Long mid;
    private String email;
    private String nickname;

    // Missing
    private Long missno;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
