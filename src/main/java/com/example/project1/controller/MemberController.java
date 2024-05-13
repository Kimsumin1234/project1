package com.example.project1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.MemberDto;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/profile")
    public void getProfile() {
        log.info("마이 페이지 요청");

    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/edit")
    public String getEditProfile() {
        log.info("회원정보수정 페이지 요청");
        return "/member/edit-profile";
    }

}
