package com.example.project1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project1.service.AdoptOAuth2UserDetailService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class HomeController {

    // private final AdoptOAuth2UserDetailService adoptOAuth2UserDetailService;

    @GetMapping("/")
    public String getHome() {
        log.info("Home 요청");
        return "index";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthenticationInfo() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

    // @PreAuthorize("permitAll()")
    // @GetMapping("/oauth2/authorization/naver")
    // public String getMethodName(OAuth2UserRequest userRequest, Model model) {
    // log.info("네이버 로그인 페이지 요청 {}", userRequest);

    // try {
    // adoptOAuth2UserDetailService.loadUser(userRequest);
    // } catch (Exception e) {
    // model.addAttribute("socialerror", e.getMessage());
    // return "/member/login";
    // }

    // return "/oauth2/authorization/naver";
    // }

}
