package com.example.project1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.dto.PasswordChangeDto;
import com.example.project1.service.AdoptUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final AdoptUserService adoptUserService;

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
    public void getEditProfile(PasswordChangeDto pDto) {
        log.info("회원정보수정 페이지 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postEditNickname(MemberDto upMemberDto, RedirectAttributes rttr) {
        log.info("닉네임 수정 요청 {}", upMemberDto);

        String msg = adoptUserService.nickNameUpdate(upMemberDto);

        // Authentication 값을 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();

        authMemberDto.getMemberDto().setNickname(upMemberDto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        rttr.addFlashAttribute("msg", msg);

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postEditPassword(PasswordChangeDto pDto, HttpSession session, Model model, RedirectAttributes rttr) {
        log.info("비밀번호 수정 요청 {}", pDto);

        if (!pDto.getNewPassword().equals(pDto.getCheckNewPassword())) {
            model.addAttribute("error2", "변경할 비밀번호와 다릅니다");
            return "/member/edit";
        }

        try {
            adoptUserService.passwordUpdate(pDto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/edit";
        }

        session.invalidate();

        return "redirect:/member/login";
    }

}
