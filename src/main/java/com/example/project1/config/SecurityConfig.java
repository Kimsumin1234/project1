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
        SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices)
                        throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/images/**", "/auth")
                                .permitAll()
                                .requestMatchers("/upload/display", "/upload/ex1").permitAll()
                                .requestMatchers("/adopt/list", "/adopt/read/**").permitAll()
                                .requestMatchers("/review/list", "/review/read").permitAll()
                                .requestMatchers("/reply/**", "/comment/**").permitAll()
                                .requestMatchers("/animalReviews/**").permitAll()
                                .requestMatchers("/missing/list", "/missing/read", "/missing/**").permitAll()
                                .requestMatchers("/missingreply/**").permitAll()
                                .requestMatchers("/member/sms", "/send-one", "/send-one2", "/send-one3", "/certif")
                                .permitAll()
                                .requestMatchers("/member/registerPage").permitAll()
                                .requestMatchers("/member/register").permitAll()
                                .requestMatchers("/member/findid", "/member/resultfindid").permitAll()
                                .requestMatchers("/member/findpwd1", "/member/findpwd2", "/member/findpwd3").permitAll()
                                // .anyRequest().permitAll());
                                .anyRequest().authenticated());
                http.formLogin(login -> login
                                .loginPage("/member/login").permitAll()
                                .defaultSuccessUrl("/", true));
                // .successHandler(adoptLoginSuccessHandler()));
                http.oauth2Login(login -> login
                                .defaultSuccessUrl("/", true)); // 공통인증
                // .successHandler(adoptLoginSuccessHandler())
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
                TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey",
                                userDetailsService,
                                encodingAlgorithm);
                rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31); // 31일, 쿠키 만료 시간 (필수로설정)
                return rememberMeServices;
        }

        @Bean
        AdoptLoginSuccessHandler adoptLoginSuccessHandler() {
                return new AdoptLoginSuccessHandler();
        }
}
