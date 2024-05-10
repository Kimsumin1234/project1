package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
