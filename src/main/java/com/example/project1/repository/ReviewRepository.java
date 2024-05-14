package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Review;

import com.example.project1.repository.search.ReviewMemberReviewReplyRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewMemberReviewReplyRepository {

}
