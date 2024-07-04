package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Missing;
import com.example.project1.entity.MissingReply;

public interface MissingReplyRepository extends JpaRepository<MissingReply, Long> {

    List<MissingReply> findByMissing(Missing missing);
}
