package com.example.project1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Modifying
    @Query("update Member m set m.nickname=?1 where m.email=?2")
    void updateNickName(String nickname, String email);
}
