package com.example.project1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.dto.ReviewImageDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;

public interface ReviewService {

    PageResultDto<ReviewDto, Object[]> getList(PageRequestDto requestDto);

    ReviewDto getRow(Long rno);

    Long reviewUpdate(ReviewDto reviewDto);

    Long reviewInsert(ReviewDto reviewDto);

    void reviewRemove(Long rno);

    void incrementViewCount(Long rno);

    Long getViewCount(Long rno);

    // entity, dto 형변환
    public default ReviewDto entityToDto(Review review, List<ReviewImage> reviewImages, Long mid, String email,
            String nickname, Long replyCount) {
        ReviewDto reviewDto = ReviewDto.builder()
                .rno(review.getRno())
                .title(review.getTitle())
                .text(review.getText())
                .mid(mid)
                .email(email)
                .nickname(nickname)
                .replyCount(replyCount != null ? replyCount : 0)
                .viewCount(review.getViewCount() != null ? review.getViewCount() : 0)
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .build();

        List<ReviewImageDto> reviewImageDtos = reviewImages.stream().map(reviewImage -> {
            return ReviewImageDto.builder()
                    .inum(reviewImage.getInum())
                    .uuid(reviewImage.getUuid())
                    .imagename(reviewImage.getImagename())
                    .path(reviewImage.getPath())
                    .build();
        }).collect(Collectors.toList());
        reviewDto.setReviewImageDtos(reviewImageDtos);

        return reviewDto;
    }

    public default Map<String, Object> dtoToEntity(ReviewDto dto) {
        Map<String, Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(dto.getMid()).build();
        Review review = Review.builder()
                .rno(dto.getRno())
                .title(dto.getTitle())
                .text(dto.getText())
                .viewCount(dto.getViewCount())
                .writer(member)
                .build();
        entityMap.put("review", review);

        List<ReviewImageDto> reviewImageDtos = dto.getReviewImageDtos();
        List<ReviewImage> reviewImages = new ArrayList<>();
        if (reviewImageDtos != null && reviewImageDtos.size() > 0) {
            for (ReviewImageDto rDto : reviewImageDtos) {
                ReviewImage reviewImage = ReviewImage.builder()
                        .review(review)
                        .imagename(rDto.getImagename())
                        .uuid(rDto.getUuid())
                        .path(rDto.getPath())
                        .build();
                reviewImages.add(reviewImage);
            }
        }
        // 변환이 끝난 entity list 를 map 담기 : put
        entityMap.put("imgList", reviewImages);

        return entityMap;
    }
}
