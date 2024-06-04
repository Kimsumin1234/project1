package com.example.project1.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AdoptLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 로그인 성공후 이전페이지로
        setDefaultTargetUrl("/");
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        log.info("핸들러 {} {}", request, response);
        log.info("savedRequest {}", savedRequest);
        if (savedRequest != null) {
            // 로그인 하기 전 url로 이동하기
            String targetUrl = savedRequest.getRedirectUrl();
            // log.info("핸들러2 {}", targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            // 기본 url로 가도록 함
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}
