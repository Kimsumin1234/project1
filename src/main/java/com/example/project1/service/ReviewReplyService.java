package com.example.project1.service;

import java.util.List;

import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;

public interface ReviewReplyService {

    List<ReviewReplyDto> getListOfReview(Long rno);

    Long addReply(ReviewReplyDto replyDto);

    void removeReply(Long replyNo);

    ReviewReplyDto getReply(Long replyNo);

    Long updateReply(ReviewReplyDto replyDto);

    public default ReviewReplyDto entityToDto(ReviewReply reviewReply) {
        return ReviewReplyDto.builder()
                .replyNo(reviewReply.getReplyNo())
                .text(reviewReply.getText())
                .rno(reviewReply.getReview().getRno())
                .mid(reviewReply.getReplyer().getMid())
                .nickname(reviewReply.getReplyer().getNickname())
                .email(reviewReply.getReplyer().getEmail())
                .createdDate(reviewReply.getCreatedDate())
                .lastModifiedDate(reviewReply.getLastModifiedDate())
                .build();

    }

    public default ReviewReply dtoToEntity(ReviewReplyDto reviewReplyDto) {

        ReviewReply reviewReply = new ReviewReply();
        reviewReply.setReplyNo(reviewReplyDto.getReplyNo());
        reviewReply.setText(reviewReplyDto.getText());
        reviewReply.setReview(Review.builder().rno(reviewReplyDto.getRno()).build());
        reviewReply.setReplyer(Member.builder().mid(reviewReplyDto.getMid()).build());
        reviewReply.setCreatedDate(reviewReplyDto.getCreatedDate());
        return reviewReply;
    }
}
