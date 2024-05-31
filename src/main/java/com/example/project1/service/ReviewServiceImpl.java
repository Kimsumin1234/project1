package com.example.project1.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Heart;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.HeartRepository;
import com.example.project1.repository.MemberRepository;
import com.example.project1.repository.ReviewImageRepository;
import com.example.project1.repository.ReviewReplyCommentRepository;
import com.example.project1.repository.ReviewReplyRepository;
import com.example.project1.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository replyRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewReplyCommentRepository commentRepository;
    private final HeartRepository heartRepository;

    @Transactional
    @Override
    public PageResultDto<ReviewDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = reviewRepository.list(requestDto.getType(),
                requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("rno").descending()));

        Function<Object[], ReviewDto> fn = (entity -> entityToDto((Review) entity[0],
                (List<ReviewImage>) Arrays.asList((ReviewImage) entity[1]), (Long) entity[2], (String) entity[3],
                (String) entity[4], (Long) entity[5], (Long) entity[6]));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public ReviewDto getRow(Long rno) {
        List<Object[]> result = reviewImageRepository.getRow(rno);

        Review review = (Review) result.get(0)[0];
        // ReviewImage reviewImage = (ReviewImage) result.get(0)[1];
        Long mid = (Long) result.get(0)[2];
        String email = (String) result.get(0)[3];
        String nickname = (String) result.get(0)[4];
        Long reviewCnt = (Long) result.get(0)[5];
        Long heartCount = (Long) result.get(0)[6];

        List<ReviewImage> reviewImages = new ArrayList<>();
        result.forEach(arr -> {
            ReviewImage reviewImage = (ReviewImage) arr[1];
            reviewImages.add(reviewImage);
        });

        return entityToDto(review, reviewImages, mid, email, nickname, reviewCnt, heartCount);
    }

    @Override
    public Long reviewInsert(ReviewDto reviewDto) {

        Map<String, Object> entityMap = dtoToEntity(reviewDto);
        Review review = reviewRepository.save((Review) entityMap.get("review"));
        List<ReviewImage> reviewImages = (List<ReviewImage>) entityMap.get("imgList");

        reviewImages.forEach(img -> reviewImageRepository.save(img));

        return review.getRno();
    }

    @Transactional
    @Override
    public Long reviewUpdate(ReviewDto reviewDto) {
        Map<String, Object> entityMap = dtoToEntity(reviewDto);

        // review 기존 image 제거
        Review review = (Review) entityMap.get("review");
        reviewImageRepository.deleteByReview(review);
        // reviewimage 삽입
        List<ReviewImage> reviewImages = (List<ReviewImage>) entityMap.get("imgList");
        reviewImages.forEach(img -> reviewImageRepository.save(img));

        reviewRepository.save((Review) entityMap.get("review"));

        return review.getRno();
    }

    @Transactional
    @Override
    public void reviewRemove(Long rno) {
        // 여기 모든 대댓글 지우고, 모든 댓글 지우고, 글 삭제

        Review review = reviewRepository.findById(rno).get();
        List<Heart> hearts = heartRepository.findByReview(review);
        for (Heart heart : hearts) {
            heartRepository.deleteById(heart.getHno());
        }
        List<ReviewReply> replies = replyRepository.findByReview(review);
        for (ReviewReply reviewReply : replies) {
            commentRepository.deleteAllByReply(reviewReply);
        }
        log.info("review remove {}", review);
        // commentRepository.deleteByReply(ReviewReply.builder().replyNo(review.getRno()).build());
        replyRepository.deleteAllByReview(review);
        reviewImageRepository.deleteByReview(review);
        reviewRepository.deleteById(rno);
    }

    @Override
    public void incrementViewCount(Long rno) {
        Optional<Review> optionReview = reviewRepository.findById(rno);
        if (optionReview.isPresent()) {
            Review review = optionReview.get();
            review.setViewCount(review.getViewCount() + 1);
            reviewRepository.save(review);
        }
    }

    @Override
    public Long getViewCount(Long rno) {
        Review review = reviewRepository.findById(rno).get();
        return review.getViewCount();
    }

    @Transactional
    @Override
    public List<ReviewDto> getHeartList(Long mid) {
        List<Heart> hearts = heartRepository.findByMember(Member.builder().mid(mid).build());
        List<Review> reviews = new ArrayList<>();
        for (Heart heart : hearts) {
            Review review = reviewRepository.findByHeart(heart.getHno());
            reviews.add(review);
        }
        return reviews.stream().map(review -> reviewEntityToDto(review))
                .collect(Collectors.toList());
    }

}
