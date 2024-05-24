package com.example.project1.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;

import com.example.project1.dto.ReviewImageDto;
import com.example.project1.dto.ReviewReplyCommentDto;
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

        ReviewReplyDto reviewReplyDto = ReviewReplyDto.builder()
                .replyNo(reviewReply.getReplyNo())
                .text(reviewReply.getText())
                .rno(reviewReply.getReview().getRno())
                .mid(reviewReply.getReplyer().getMid())
                .nickname(reviewReply.getReplyer().getNickname())
                .email(reviewReply.getReplyer().getEmail())
                .createdDate(reviewReply.getCreatedDate())
                .lastModifiedDate(reviewReply.getLastModifiedDate())
                .build();

        List<ReviewReplyCommentDto> commentDtos = reviewReply.getReplyComment().stream()
                .map(comment -> {
                    return ReviewReplyCommentDto.builder()
                            .commentNo(comment.getCommentNo())
                            .text(comment.getText())
                            .mid(comment.getReplyer().getMid())
                            .email(comment.getReplyer().getEmail())
                            .nickname(comment.getReplyer().getNickname())
                            .replyNo(comment.getReply().getReplyNo())
                            .createdDate(comment.getCreatedDate())
                            .lastModifiedDate(comment.getLastModifiedDate())
                            .build();
                }).sorted(Comparator.comparingLong(ReviewReplyCommentDto::getCommentNo)).collect(Collectors.toList());
        reviewReplyDto.setComments(commentDtos);

        return reviewReplyDto;
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
