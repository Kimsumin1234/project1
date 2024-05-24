package com.example.project1.service;

import java.util.List;

import com.example.project1.dto.ReviewReplyCommentDto;
import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.ReviewReply;
import com.example.project1.entity.ReviewReplyComment;

public interface ReviewReplyCommentService {

    List<ReviewReplyCommentDto> getListOfComment(Long replyNo);

    Long addComment(ReviewReplyCommentDto commentDto);

    void removeComment(Long commentNo);

    ReviewReplyCommentDto getComment(Long commentNo);

    Long updateComment(ReviewReplyCommentDto commentNo);

    public default ReviewReplyCommentDto entityToDto(ReviewReplyComment comment) {
        return ReviewReplyCommentDto.builder()
                .commentNo(comment.getCommentNo())
                .text(comment.getText())
                .replyNo(comment.getReply().getReplyNo())
                .mid(comment.getReplyer().getMid())
                .nickname(comment.getReplyer().getNickname())
                .email(comment.getReplyer().getEmail())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();

    }

    public default ReviewReplyComment dtoToEntity(ReviewReplyCommentDto commentDto) {

        ReviewReplyComment comment = new ReviewReplyComment();
        comment.setCommentNo(commentDto.getCommentNo());
        comment.setText(commentDto.getText());
        comment.setReply(ReviewReply.builder().replyNo(commentDto.getReplyNo()).build());
        comment.setReplyer(Member.builder().mid(commentDto.getMid()).build());
        comment.setCreatedDate(commentDto.getCreatedDate());
        return comment;
    }
}
