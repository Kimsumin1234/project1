package com.example.project1.service;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;

public interface ReviewService {

    // PageResultDto<ReviewDto, Object[]> getList(PageRequestDto requestDto);

    // entity, dto 형변환
    public default ReviewDto entityToDto(Review review, Member member, Long replyCount) {
        return ReviewDto.builder()
                .rno(review.getRno())
                .title(review.getTitle())
                .text(review.getText())
                .mid(member.getMid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .replyCount(replyCount != null ? replyCount : 0)
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .build();
    }

    public default Review dtoToEntity(ReviewDto dto) {
        Member member = Member.builder().mid(dto.getMid()).build();
        return Review.builder()
                .rno(dto.getRno())
                .title(dto.getTitle())
                .text(dto.getText())
                .writer(member)
                .build();
    }
}
