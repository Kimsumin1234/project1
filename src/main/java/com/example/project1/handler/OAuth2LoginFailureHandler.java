package com.example.project1.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.info("소셜로그인 실패!!! {}", request.getParameterMap());
        // request.setAttribute("oauth2error", exception.getMessage());
        // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        // response.getWriter().write("소셜 로그인 실패!");
        HttpSession session = request.getSession();
        session.setAttribute("oauth", "소셜 로그인 실패");

        response.sendRedirect("/member/login");

    }

}
