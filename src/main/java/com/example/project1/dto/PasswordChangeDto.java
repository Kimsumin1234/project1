package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordChangeDto {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String checkNewPassword;
}
