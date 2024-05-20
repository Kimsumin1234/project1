package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.ReviewReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewReplyServiceImpl implements ReviewReplyService {

    private final ReviewReplyRepository replyRepository;

    @Override
    public List<ReviewReplyDto> getListOfReview(Long rno) {
        Review review = Review.builder().rno(rno).build();
        List<ReviewReply> reviewReplys = replyRepository.getReviewRepliesByReviewOrderByReplyNo(review);

        return reviewReplys.stream().map(reviewReply -> entityToDto(reviewReply)).collect(Collectors.toList());
    }

    @Override
    public Long addReply(ReviewReplyDto replyDto) {

        return replyRepository.save(dtoToEntity(replyDto)).getReplyNo();
    }

    @Override
    public void removeReply(Long replyNo) {
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
