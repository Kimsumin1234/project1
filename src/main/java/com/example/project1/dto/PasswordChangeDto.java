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
public class PasswordChangeDto {
    // edit-profile.html 의 name 속성과 맞춰준다
    private String email;
    private String currentPassword;
    private String newPassword;
    private String checkNewPassword;
}
