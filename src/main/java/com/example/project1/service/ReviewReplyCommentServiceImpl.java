package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.project1.dto.ReviewReplyCommentDto;
import com.example.project1.entity.ReviewReply;
import com.example.project1.entity.ReviewReplyComment;
import com.example.project1.repository.ReviewReplyCommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewReplyCommentServiceImpl implements ReviewReplyCommentService {

    public final ReviewReplyCommentRepository commentRepository;

    @Override
    public List<ReviewReplyCommentDto> getListOfComment(Long replyNo) {
        log.info("comment replyNo {}", replyNo);
        ReviewReply reviewReply = ReviewReply.builder().replyNo(replyNo).build();
        List<ReviewReplyComment> comments = commentRepository.findByReplyOrderByCommentNo(reviewReply);

        return comments.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public Long addComment(ReviewReplyCommentDto commentDto) {
        log.info("comment add {}", commentDto);
        return commentRepository.save(dtoToEntity(commentDto)).getCommentNo();
    }

    @Override
    public ReviewReplyCommentDto getComment(Long commentNo) {
        log.info("comment getRow {}", commentNo);
        ReviewReplyComment reviewReplyComment = commentRepository.findById(commentNo).get();
        return entityToDto(reviewReplyComment);
    }

}
