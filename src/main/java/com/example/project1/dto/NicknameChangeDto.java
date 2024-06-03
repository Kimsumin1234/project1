package com.example.project1.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NicknameChangeDto {

    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

}
