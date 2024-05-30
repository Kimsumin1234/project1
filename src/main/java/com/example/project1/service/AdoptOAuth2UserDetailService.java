package com.example.project1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.project1.constant.MemberRole;
import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.entity.Member;
import com.example.project1.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class AdoptOAuth2UserDetailService extends DefaultOAuth2UserService {
    // 공통인증 로그인 방식
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    // load 만 치면 자동완성
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 소셜로그인 공통인증 방식으로 로그인하면 뜨는 정보들
        log.info("========================================");
        log.info("userRequest : {}", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName : {}", clientName);
        log.info("Token : {}", userRequest.getAccessToken());
        log.info("Client : {}", userRequest.getClientRegistration());
        log.info("========================================");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            String getAttr = new ObjectMapper().writeValueAsString(oAuth2User.getAttributes());
            log.info("getAttr : {}", getAttr);
            log.info("========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 네이버 로그인
        if (clientName.equals("naver")) {
            log.info("response {}", oAuth2User.getAttributes().get("response"));
            log.info("========================================");
            Map<String, String> map = (Map<String, String>) oAuth2User.getAttributes().get("response");
            String nEmail = map.get("email");
            String nName = map.get("name");
            String nPhone = map.get("mobile");
            log.info("info {} {} {}", nEmail, nName, nPhone);
            log.info("========================================");

            Member member = saveSocialMember(nEmail, nName, nPhone);
            return new AuthMemberDto(entityToDto(member), true);
        }

        // 구글 로그인
        // 소셜로그인 을 하면 DB테이블에 저장하는 작업 (회원가입?)
        Member member = saveSocialMember(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("name"));
        return new AuthMemberDto(entityToDto(member), true);
    }

    private Member saveSocialMember(String email, String name) {
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            // 찾은 이메일이 회원이 되있으면 여기서 끝
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .nickname(name)
                .password(passwordEncoder.encode("1111")) // 임의 지정
                .fromSocial(true) // 소셜로그인
                .role(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);
        return member;
    }

    private Member saveSocialMember(String email, String name, String phone) {
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            // 찾은 이메일이 회원이 되있으면 여기서 끝
            return result.get();
        }

        String rePhone = phone.replace("-", "");
        Member member = Member.builder()
                .email(email)
                .nickname(name)
                .password(passwordEncoder.encode("1111")) // 임의 지정
                .phone(rePhone)
                .fromSocial(true) // 소셜로그인
                .role(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);
        return member;
    }

    private MemberDto entityToDto(Member member) {
        return MemberDto.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .phone(member.getPhone())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .fromSocial(member.isFromSocial())
                .role(member.getRole())
                .createdDate(member.getCreatedDate())
                .lastModifiedDate(member.getLastModifiedDate())
                .build();
    }
}
