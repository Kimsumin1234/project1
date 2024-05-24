package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.ReviewReplyCommentRepository;
import com.example.project1.repository.ReviewReplyRepository;
import com.example.project1.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewReplyServiceImpl implements ReviewReplyService {

    private final ReviewReplyRepository replyRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReplyCommentRepository commentRepository;

    @Transactional
    @Override
    public List<ReviewReplyDto> getListOfReview(Long rno) {
        // Review review = Review.builder().rno(rno).build();
        // List<ReviewReply> reviewReplys =
        // replyRepository.getReviewRepliesByReviewOrderByReplyNo(review);
        List<ReviewReply> reviewReplys = reviewRepository.getReviewReplies(rno);

        return reviewReplys.stream().map(reviewReply -> entityToDto(reviewReply)).collect(Collectors.toList());
    }

    @Override
    public Long addReply(ReviewReplyDto replyDto) {

        return replyRepository.save(dtoToEntity(replyDto)).getReplyNo();
    }

    @Transactional
    @Override
    public void removeReply(Long replyNo) {
        commentRepository.deleteByReply(ReviewReply.builder().replyNo(replyNo).build());
        replyRepository.deleteById(replyNo);
    }

    @Override
    public ReviewReplyDto getReply(Long replyNo) {
        ReviewReply reviewReply = replyRepository.findById(replyNo).get();
        return entityToDto(reviewReply);
    }

    @Override
    public Long updateReply(ReviewReplyDto replyDto) {
        return replyRepository.save(dtoToEntity(replyDto)).getReplyNo();
    }

}
