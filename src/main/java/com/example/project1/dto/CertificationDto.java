package com.example.project1.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CertificationDto {

    private String email;

    @Pattern(regexp = "^(01[016789]{1})[0-9]{3,4}[0-9]{4}$", message = "- 를 제외한 휴대폰 번호를 입력해주세요.")
    private String phone;

    private String certNum;
}
