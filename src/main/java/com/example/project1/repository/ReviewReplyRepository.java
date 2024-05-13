package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {

    // 에러가 안뜨는 것부터 주석 해제
    List<ReviewReply> getReviewRepliesByReviewOrderByReplyNo(Review review);

}
