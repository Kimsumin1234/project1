package com.example.project1.service;

import com.example.project1.dto.HeartDto;

import com.example.project1.entity.Heart;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;

public interface HeartService {

    HeartDto getHeart(Long mid, Long rno);

    void addHeart(HeartDto heartDto);

    void deleteHeart(HeartDto heartDto);

    Long reviewHeart(Long rno);

    public default HeartDto entityToDto(Heart heart) {
        return HeartDto.builder()
                .hno(heart.getHno())
                .memberId(heart.getMember().getMid())
                .reviewRno(heart.getReview().getRno())
                .build();
    }

    public default Heart dtoToEntity(HeartDto heartDto) {
        Member member = Member.builder().mid(heartDto.getMemberId()).build();
        Review review = Review.builder().rno(heartDto.getReviewRno()).build();

        Heart heart = new Heart();
        heart.setHno(heartDto.getHno());
        heart.setMember(member);
        heart.setReview(review);

        return heart;
    }
}
