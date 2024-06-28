package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Missing;
import com.example.project1.repository.missing.MissingMemberReviewRepository;

public interface MissingRepository extends JpaRepository<Missing, Long>, MissingMemberReviewRepository {

}
