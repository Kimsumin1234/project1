package com.example.project1.service;

import com.example.project1.dto.MemberDto;
import com.example.project1.dto.PasswordChangeDto;
import com.example.project1.entity.Member;

public interface AdoptUserService {

    // 회원가입
    String register(MemberDto insertDto) throws IllegalStateException;

    // 닉네임 수정
    String nickNameUpdate(MemberDto upMemberDto) throws IllegalStateException;

    // 비밀번호 수정
    void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException;

    // 회원탈퇴
    String leave(MemberDto leaveMemberDto) throws IllegalStateException;

    // 휴대폰번호 중복
    void validateDuplicationMemberPhone(String phone) throws IllegalStateException;

    // 아이디 찾기
    MemberDto findId(String phone) throws IllegalStateException;

    // 아이디 찾기
    void findIdEmail(String email) throws IllegalStateException;

    // 휴대폰 번호랑 이메일 일치
    void equalPhoneEmail(String phone, String email) throws IllegalStateException;

    // dto => entity
    public default Member dtoToEntity(MemberDto memberDto) {
        return Member.builder()
                .email(memberDto.getEmail())
                .phone(memberDto.getPhone())
                .nickname(memberDto.getNickname())
                .password(memberDto.getPassword())
                .role(memberDto.getRole())
                .build();
    }

    // entity => dto
    public default MemberDto entityToDto(Member member) {
        return MemberDto.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .phone(member.getPhone())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .role(member.getRole())
                .createdDate(member.getCreatedDate())
                .lastModifiedDate(member.getLastModifiedDate())
                .build();
    }
}
