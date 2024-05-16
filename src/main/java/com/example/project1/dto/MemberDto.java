package com.example.project1.dto;

import java.time.LocalDateTime;

import com.example.project1.constant.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class MemberDto {
    private Long mid;

    private String email;

    private String phone;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String password;

    private String checkPassword;

    private MemberRole role;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
