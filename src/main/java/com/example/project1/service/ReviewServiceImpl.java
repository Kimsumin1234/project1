package com.example.project1.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.ReviewImageRepository;
import com.example.project1.repository.ReviewReplyRepository;
import com.example.project1.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository replyRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    @Override
    public PageResultDto<ReviewDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = reviewRepository.list(requestDto.getType(),
                requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("rno").descending()));

        Function<Object[], ReviewDto> fn = (entity -> entityToDto((Review) entity[0],
                (List<ReviewImage>) Arrays.asList((ReviewImage) entity[1]), (Long) entity[2], (String) entity[3],
                (String) entity[4], (Long) entity[5]));
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

        List<ReviewImage> reviewImages = new ArrayList<>();
        result.forEach(arr -> {
            ReviewImage reviewImage = (ReviewImage) arr[1];
            reviewImages.add(reviewImage);
        });

        return entityToDto(review, reviewImages, mid, email, nickname, reviewCnt);
    }

    @Override
    public Long reviewInsert(ReviewDto reviewDto) {

        Map<String, Object> entityMap = dtoToEntity(reviewDto);
        Review review = reviewRepository.save((Review) entityMap.get("review"));
        List<ReviewImage> reviewImages = (List<ReviewImage>) entityMap.get("imgList");

        reviewImages.forEach(img -> reviewImageRepository.save(img));

        return review.getRno();
    }

}
