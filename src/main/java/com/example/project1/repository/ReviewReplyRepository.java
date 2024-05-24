package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;
import com.example.project1.entity.ReviewReplyComment;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {

    List<ReviewReply> getReviewRepliesByReviewOrderByReplyNo(Review review);

    @Modifying
    @Query("delete from ReviewReply rr where rr.review = :review")
    void deleteAllByReview(Review review);

    List<ReviewReply> findByReplyComment(List<ReviewReplyComment> replyComment);

    List<ReviewReply> findByReview(Review review);
}
