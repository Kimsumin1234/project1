package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.ReviewImage;
import com.example.project1.repository.search.ReviewMemberReviewReplyRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, ReviewMemberReviewReplyRepository {

}
