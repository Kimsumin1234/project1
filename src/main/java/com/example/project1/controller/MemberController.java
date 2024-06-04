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
import com.example.project1.dto.CertificationDto;
import com.example.project1.dto.FindPasswordDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.dto.NicknameChangeDto;
import com.example.project1.dto.PasswordChangeDto;
import com.example.project1.service.AdoptUserService;

import jakarta.servlet.http.HttpServletRequest;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/animalCarts")
    public void animalCart() {
        log.info("입양내역조회");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile() {
        log.info("마이 페이지 요청");

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public void getEditProfile(PasswordChangeDto pDto, NicknameChangeDto nicknameChangeDto) {
        log.info("회원정보수정 페이지 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postEditNickname(@Valid NicknameChangeDto nicknameChangeDto, BindingResult result,
            PasswordChangeDto pDto, RedirectAttributes rttr, Model model) {
        log.info("닉네임 수정 요청 {}", nicknameChangeDto);

        if (result.hasErrors()) {
            return "/member/edit";
        }

        String msg = "";

        try {
            msg = adoptUserService.nickNameUpdate(nicknameChangeDto);
        } catch (Exception e) {
            model.addAttribute("error3", e.getMessage());
            return "/member/edit";
        }

        // Authentication 값을 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();

        authMemberDto.getMemberDto().setNickname(nicknameChangeDto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        rttr.addFlashAttribute("msg", msg);

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postEditPassword(@Valid PasswordChangeDto pDto, BindingResult result,
            NicknameChangeDto nicknameChangeDto, HttpSession session, Model model, RedirectAttributes rttr) {
        log.info("비밀번호 수정 요청 {}", pDto);

        if (result.hasErrors()) {
            return "/member/edit";
        }

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/leave")
    public void getLeave(MemberDto memberDto) {
        log.info("회원탈퇴 페이지 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/leave")
    public String postLeave(RedirectAttributes rttr, HttpSession session, MemberDto leaveMemberDto, Model model) {
        log.info("회원탈퇴 요청 {}", leaveMemberDto);

        if (!leaveMemberDto.getPassword().equals(leaveMemberDto.getCheckPassword())) {
            model.addAttribute("error2", "비밀번호 확인을 다시 해주세요.");
            return "/member/leave";
        }

        String msg = "";

        try {
            msg = adoptUserService.leave(leaveMemberDto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/leave";
        }

        // 회원탈퇴 성공하면 세션 날리기
        session.invalidate();

        rttr.addFlashAttribute("msg", msg);

        return "redirect:/";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/sms")
    public void getSms(CertificationDto cDto, MemberDto memberDto) {
        log.info("문자인증 페이지 요청 {}", cDto);
    }

    @PostMapping("/registerPage")
    public String postRegisterPage(@Valid CertificationDto cDto, BindingResult result, MemberDto memberDto,
            HttpSession session, Model model, RedirectAttributes rttr) {
        log.info("회원가입 페이지 요청 {}", memberDto);
        // 유효성 검사
        if (result.hasErrors()) {
            return "/member/sms";
        }

        try {
            adoptUserService.validateDuplicationMemberPhone(cDto.getPhone());
        } catch (IllegalStateException e) {
            model.addAttribute("dupliError", e.getMessage());
            return "/member/sms";
        }

        if (!cDto.getCertNum().equals(session.getAttribute("rNum"))) {
            model.addAttribute("smsError", "인증번호를 다시 확인해주세요.");
            return "/member/sms";
        } else {
            // session.invalidate(); // 이거 때문에 이유는 모르지만 문제가 생겼음
            // rttr.addAttribute("memberDtoPhone", memberDto.getPhone());
            // return "redirect:/member/register";
            return "/member/register";
        }

    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto insertDto, BindingResult result, RedirectAttributes rttr, Model model) {
        log.info("회원가입 요청 {}", insertDto);
        // 유효성 검사
        if (result.hasErrors()) {
            return "/member/register";
        }

        if (!insertDto.getPassword().equals(insertDto.getCheckPassword())) {
            model.addAttribute("error2", "비밀번호 확인을 다시 해주세요.");
            return "/member/register";
        }

        String newEmail = "";
        // 중복 이메일 검사
        try {
            newEmail = adoptUserService.register(insertDto);
        } catch (Exception e) {
            model.addAttribute("Exception", e.getMessage());
            return "/member/register";
        }

        rttr.addFlashAttribute("newEmail", newEmail);
        return "redirect:/member/login";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/findid")
    public void getFindId(CertificationDto cDto, MemberDto memberDto) {
        log.info("아이디찾기 페이지 요청 {}", cDto);
    }

    @PostMapping("/resultfindid")
    public String postFindId(HttpSession session, Model model) {
        log.info("찾는 아이디 session:{}", session);

        model.addAttribute("email", session.getAttribute("findId"));
        session.invalidate();
        return "/member/resultfindid";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/findpwd1")
    public void getFindPwd(CertificationDto cDto, FindPasswordDto fDto) {
        log.info("비밀번호찾기 페이지1 요청 {}", fDto);
    }

    @PostMapping("/findpwd1")
    public String postFindPwd2(CertificationDto cDto, FindPasswordDto fDto, RedirectAttributes rttr, Model model) {
        log.info("비밀번호찾기 페이지2 요청 {}", fDto);

        try {
            adoptUserService.findIdEmail(fDto.getEmail());
        } catch (IllegalStateException e) {
            model.addAttribute("dupliError", e.getMessage());
            return "/member/findpwd1";
        }

        return "/member/findpwd2";
    }

    @PostMapping("/findpwd2")
    public String postMethodName(@Valid CertificationDto cDto, BindingResult result, FindPasswordDto fDto,
            HttpSession session, Model model) {
        log.info("비밀번호찾기 페이지3 요청 {}", fDto);

        // 유효성 검사
        if (result.hasErrors()) {
            return "/member/findpwd2";
        }

        try {
            adoptUserService.equalPhoneEmail(cDto.getPhone(), fDto.getEmail());
        } catch (IllegalStateException e) {
            model.addAttribute("dupliError", e.getMessage());
            return "/member/findpwd2";
        }

        if (!cDto.getCertNum().equals(session.getAttribute("rNum"))) {
            model.addAttribute("smsError", "인증번호를 다시 확인해주세요.");
            return "/member/findpwd2";
        } else {
            // session.invalidate();
            return "/member/findpwd3";
        }

    }

    @PostMapping("/findpwd3")
    public String postFindPassword(@Valid FindPasswordDto fDto, BindingResult result, CertificationDto cDto,
            Model model, RedirectAttributes rttr) {
        log.info("비밀번호 수정 요청 {}", fDto);

        if (result.hasErrors()) {
            return "/member/findpwd3";
        }

        if (!fDto.getNewPassword().equals(fDto.getCheckNewPassword())) {
            model.addAttribute("findPwdError", "변경할 비밀번호와 다릅니다");
            return "/member/findpwd3";
        }

        adoptUserService.findPasswordUpdate(fDto);

        return "redirect:/member/login";
    }

}
