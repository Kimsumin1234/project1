package com.example.project1.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.entity.Member;
import com.example.project1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AdoptUserServiceImpl implements UserDetailsService, AdoptUserService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 메소드
        log.info("로그인 요청 {}", username);

        Optional<Member> result = memberRepository.findByEmail(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }
        Member member = result.get();

        return new AuthMemberDto(entityToDto(member));
    }

    @Transactional
    @Override
    public String nickNameUpdate(MemberDto upMemberDto) {
        log.info("닉네임 수정 ServiceImpl {}", upMemberDto);

        memberRepository.updateNickName(upMemberDto.getNickname(), upMemberDto.getEmail());
        return "닉네임 수정 완료";
    }

}
