package com.example.project1.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.project1.constant.MemberRole;
import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.entity.Member;
import com.example.project1.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException, IllegalStateException {
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
            String nId = map.get("id");
            log.info("info {} {} {} {}", nEmail, nName, nPhone, nId);
            log.info("========================================");

            // Member member = saveSocialMember(nEmail, nName + " for naver", nPhone);
            Member member = saveSocialMember(nEmail, nId);
            member.setNickname("(Guest)Naver" + nId.substring(27, 31));

            return new AuthMemberDto(entityToDto(member), true);

        }

        // 카카오 로그인
        if (clientName.equals("kakao")) {
            String kUserId = oAuth2User.getAttributes().get("id").toString();
            Map<String, String> kMap = (Map<String, String>) oAuth2User.getAttributes().get("properties");
            String kNickname = kMap.get("nickname");
            log.info("info : {} , {}", kUserId, kNickname);
            log.info("========================================");

            String kEmail = "kakao" + kUserId.substring(0, 5) + "@kakao.com";
            Optional<Member> result = memberRepository.findByEmailAndFromSocial(kEmail, true);
            if (result.isPresent()) {
                return new AuthMemberDto(entityToDto(result.get()), true);
            } else {
                Member member = Member.builder()
                        .email("kakao" + kUserId.substring(0, 5) + "@kakao.com")
                        .nickname("(Guest)Kakao" + kUserId.substring(0, 5))
                        .password(passwordEncoder.encode("1111"))
                        .fromSocial(true)
                        .checkPhone(false)
                        .role(MemberRole.MEMBER)
                        .build();
                memberRepository.save(member);
                return new AuthMemberDto(entityToDto(member), true);
            }
        }

        // 구글 로그인
        // 소셜로그인 을 하면 DB테이블에 저장하는 작업 (회원가입?)
        Member member = saveSocialMember(oAuth2User.getAttribute("email"),
                oAuth2User.getAttribute("sub"));
        return new AuthMemberDto(entityToDto(member), true);
    }

    private Member saveSocialMember(String email, String id) throws OAuth2AuthenticationException {
        Optional<Member> result2 = memberRepository.findByEmailAndFromSocial(email, false);
        if (result2.isPresent()) {

            throw new OAuth2AuthenticationException("OAuth2Error");
        }

        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);
        if (result.isPresent()) {
            // 찾은 이메일이 회원이 되있으면 여기서 끝
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .nickname("(Guest)Google" + id.substring(3, 8))
                .password(passwordEncoder.encode("1111")) // 임의 지정
                .fromSocial(true) // 소셜로그인
                .checkPhone(false)
                .role(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);
        return member;
    }

    private Member saveSocialMember(String email, String name, String phone) throws IllegalStateException {

        String rePhone = phone.replace("-", "");

        Optional<Member> member2 = memberRepository.findByPhoneAndFromSocial(rePhone, false);
        if (member2.isPresent()) {
            log.info("예외발생 : {}", member2);
            throw new IllegalStateException("사이트에 동일한 핸드폰번호로 가입한 회원이 존재합니다.");
        }

        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);
        if (result.isPresent()) {
            // 찾은 이메일이 회원이 되있으면 여기서 끝
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .nickname(name)
                .password(passwordEncoder.encode("1111")) // 임의 지정
                .phone(rePhone)
                .checkPhone(true)
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
                .checkPhone(member.isCheckPhone())
                .role(member.getRole())
                .createdDate(member.getCreatedDate())
                .lastModifiedDate(member.getLastModifiedDate())
                .build();
    }
}
