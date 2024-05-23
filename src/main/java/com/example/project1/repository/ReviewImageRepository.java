package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.repository.search.ReviewMemberReviewReplyReviewReplyCommentRepository;

public interface ReviewImageRepository
        extends JpaRepository<ReviewImage, Long>, ReviewMemberReviewReplyReviewReplyCommentRepository {

    @Modifying
    @Query("delete from ReviewImage ri where ri.review = :review")
    void deleteByReview(Review review);

}
