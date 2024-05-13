package com.example.project1.service;

import com.example.project1.dto.MemberDto;
import com.example.project1.entity.Member;

public interface AdoptUserService {

    // 닉네임 수정
    String nickNameUpdate(MemberDto upMemberDto);

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
