package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {

    List<ReviewReply> getReviewRepliesByReviewOrderByReplyNo(Review review);

}
