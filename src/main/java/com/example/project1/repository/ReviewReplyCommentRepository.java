package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.ReviewReply;
import com.example.project1.entity.ReviewReplyComment;
import java.util.List;

public interface ReviewReplyCommentRepository extends JpaRepository<ReviewReplyComment, Long> {

    List<ReviewReplyComment> findByReplyOrderByCommentNo(ReviewReply reply);

    void deleteByReply(ReviewReply reply);

    @Modifying
    @Query("delete from ReviewReplyComment rrc where rrc.reply = :reply")
    void deleteAllByReply(ReviewReply reply);

}
