package com.example.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project1.constant.MemberRole;
import com.example.project1.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(0, 9).forEach(i -> {
            Member member = Member.builder()
                    .email("member" + i + "@naver.com")
                    .nickname("User" + i)
                    .phone("010-1234-123" + i)
                    .password(passwordEncoder.encode("1111"))
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }
}