package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.search.ReviewMemberReviewReplyReviewReplyCommentRepository;

public interface ReviewRepository
        extends JpaRepository<Review, Long>, ReviewMemberReviewReplyReviewReplyCommentRepository {

    @Query("select r from ReviewReply r where r.review.rno=:rno")
    List<ReviewReply> getReviewReplies(Long rno);
}
