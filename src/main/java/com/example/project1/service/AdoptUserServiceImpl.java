package com.example.project1.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.dto.PasswordChangeDto;
import com.example.project1.entity.Member;
import com.example.project1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AdoptUserServiceImpl implements UserDetailsService, AdoptUserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

        // 중복 검사
        validateDuplicationMemberNickName(upMemberDto.getNickname());

        memberRepository.updateNickName(upMemberDto.getNickname(), upMemberDto.getEmail());
        return "닉네임 수정 완료";
    }

    @Override
    public void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException {
        log.info("비밀번호 수정 service {}", pDto);

        Member member = memberRepository.findByEmail(pDto.getEmail()).get();

        if (!passwordEncoder.matches(pDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다.");
        } else {
            member.setPassword(passwordEncoder.encode(pDto.getNewPassword()));
            memberRepository.save(member);
        }
    }

    // 닉네임 중복 확인
    private void validateDuplicationMemberNickName(String nickname) throws IllegalStateException {
        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.isPresent()) {
            throw new IllegalStateException("중복된 닉네임 입니다.");
        }
    }
}
