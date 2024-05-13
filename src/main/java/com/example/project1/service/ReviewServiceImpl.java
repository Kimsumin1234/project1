package com.example.project1.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.ReviewReplyRepository;
import com.example.project1.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository replyRepository;

    @Override
    public PageResultDto<ReviewDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = reviewRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("rno").descending()));

        Function<Object[], ReviewDto> fn = (entity -> entityToDto((Review) entity[0],
                (Member) entity[1], (Long) entity[2]));
        return new PageResultDto<>(result, fn);
    }

}
