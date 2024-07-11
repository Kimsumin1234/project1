package com.example.project1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.AnimalItem;
import com.example.project1.service.AdoptApiService;
import com.example.project1.service.AnimalCartService;
import com.example.project1.service.AnimalHeartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.project1.dto.AnimalCartDto;
import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.dto.PageRequestDto;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/adopt")
public class AnimalController {

    private final AdoptApiService service;

    private final AnimalCartService animalCartService;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("입양 리스트 페이지 요청");

        PageResultDto<AnimalDto, Object[]> result = service.getList(requestDto);

        System.out.println("list" + result.getDtoList());

        model.addAttribute("result", result);

    }

    @GetMapping("/read")
    public void getRead(@RequestParam Long sId, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto,
            HttpServletRequest request) {
        log.info("read 요청 {}", sId);

        model.addAttribute("dto", service.getRow(sId));

        boolean jjim = false;

        // // AnimalItem 정보를 가져오는 예시
        MemberDto member = getMemberFromSession();

        if (member != null) {
            // 조회한 동물이 내 카트에 담겼는지 확인
            AnimalItem animalItem = animalCartService.findItemsByCartIdAndSId(sId, member.getMid());

            if (animalItem != null) {
                jjim = true;
            }
        } else {
            // 로그인하지 않은 사용자일 경우, jjim을 false로 설정
            jjim = false;
        }

        model.addAttribute("jjim", jjim);
    }

    // 현재 로그인 되어있는 멤버정보 가져오기
    private MemberDto getMemberFromSession() {
        log.info("세션 가져오기");

        // 세션에서 SecurityContext 가져오기
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // Principal이 문자열일 경우 처리
        if (authentication.getPrincipal() instanceof String) {
            String principalString = (String) authentication.getPrincipal();
            return null; // 예시에 따라 반환 값을 설정
        }

        // 사용자 정보 가져오기 (여기서는 AuthMemberDto를 사용)
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        MemberDto memberDto = authMemberDto.getMemberDto();

        log.info("세션 memberDto {}", memberDto);

        return memberDto;
    }

}