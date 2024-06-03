package com.example.project1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.project1.handler.AdoptLoginSuccessHandler;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/member/profile").hasAnyRole("MEMBER")
                .requestMatchers("/member/register").permitAll()
                .requestMatchers("/animal/read").permitAll()
                .anyRequest().permitAll());
        http.formLogin(login -> login
                .loginPage("/member/login").permitAll()
                .successHandler(adoptLoginSuccessHandler()));
        http.oauth2Login(login -> login.successHandler(adoptLoginSuccessHandler())); // 공통인증
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));
        http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        // http.csrf(csrf -> csrf.disable()); // csrf 비활성화
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        // RememberMeTokenAlgorithm.SHA256 : 비밀번호 알고리즘 (암호화 시켜서 저장)
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        // TokenBased : Token 기반의 쿠키
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey", userDetailsService,
                encodingAlgorithm);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31); // 7일, 쿠키 만료 시간 (필수로설정)
        return rememberMeServices;
    }

    @Bean
    AdoptLoginSuccessHandler adoptLoginSuccessHandler() {
        return new AdoptLoginSuccessHandler();
    }
}
