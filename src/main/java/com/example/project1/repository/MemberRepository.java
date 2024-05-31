package com.example.project1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Member;
import java.util.List;
import com.example.project1.entity.Heart;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhone(String phone);

    @Modifying
    @Query("update Member m set m.nickname=?1 where m.email=?2")
    void updateNickName(String nickname, String email);

    // 장바구니 추가
    Optional<Member> findByMid(Long mid);

}
