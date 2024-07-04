package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Missing;
import com.example.project1.entity.MissingReply;
import com.example.project1.repository.missing.MissingMemberReviewRepository;

public interface MissingRepository extends JpaRepository<Missing, Long>, MissingMemberReviewRepository {

    @Query("select r from MissingReply r where r.missing.missno=:missno Order By r.missrno")
    List<MissingReply> getMissingReplies(Long missno);
}
