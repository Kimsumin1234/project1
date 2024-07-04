package com.example.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project1.constant.MemberRole;
import com.example.project1.entity.Member;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    // @Transactional
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("member" + i + "@naver.com")
                    .nickname("User" + i)
                    .phone("0101234123" + i)
                    .checkPhone(true)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
        // 데이터가 제대로 저장되었는지 확인하기 위해 flush를 호출합니다.
        memberRepository.flush();
    }

}
