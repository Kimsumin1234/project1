package com.example.project1.repository;

import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.repository.search.ReviewMemberReviewReplyReviewReplyCommentHeartRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewImageRepository
  extends
    JpaRepository<ReviewImage, Long>,
    ReviewMemberReviewReplyReviewReplyCommentHeartRepository {
  @Modifying
  @Query("delete from ReviewImage ri where ri.review = :review")
  void deleteByReview(Review review);

  // 어제 날짜 이미지 가져오기 (디비버에서 쿼리문 작성함)
  // @Query(
  //   value = "SELECT * FROM REVIEW_IMAGE ri WHERE ri.PATH = TO_CHAR(SYSDATE -1,'yyyy\\mm\\dd')",
  //   nativeQuery = true
  // )
  // List<ReviewImage> getOldReviewImages();

  @Query(
    "SELECT r FROM ReviewImage r WHERE r.createdDate BETWEEN :start AND :end"
  )
  List<ReviewImage> findImagesByCreatedDateBetween(
    @Param("start") LocalDateTime start,
    @Param("end") LocalDateTime end
  );
}
