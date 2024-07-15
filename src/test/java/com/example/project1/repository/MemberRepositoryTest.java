package com.example.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.constant.MemberRole;
import com.example.project1.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private ReviewReplyCommentRepository reviewReplyCommentRepository;

    @Autowired
    private ReviewReplyRepository reviewReplyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Test
    // public void memberInsertTest() {
    // IntStream.rangeClosed(0, 9).forEach(i -> {
    // Member member = Member.builder()
    // .email("member" + i + "@naver.com")
    // .nickname("User" + i)
    // .phone("0101234123" + i)
    // .checkPhone(true)
    // .password(passwordEncoder.encode("1111"))
    // .fromSocial(false)
    // .role(MemberRole.MEMBER)
    // .build();
    // memberRepository.save(member);
    // });
    // }

    // @Test
    // public void memberInsertTest2() {
    // IntStream.rangeClosed(20, 30).forEach(i -> {
    // Member member = Member.builder()
    // .email("user" + i + "@naver.com")
    // .nickname("User" + i)
    // .phone("010333312" + i)
    // .checkPhone(true)
    // .password(passwordEncoder.encode("1111"))
    // .fromSocial(false)
    // .role(MemberRole.MEMBER)
    // .build();
    // memberRepository.save(member);
    // });
    // }

    // @Transactional
    // @Test
    // public void memberLeaveTest() {
    // Member member = Member.builder().mid(49L).build();

    // heartRepository.deleteByMember(member);
    // reviewReplyCommentRepository.deleteByMember(member);
    // reviewReplyRepository.deleteByMember(member);

    // reviewRepository.deleteByMember(member);

    // memberRepository.delete(member);
    // }

}
