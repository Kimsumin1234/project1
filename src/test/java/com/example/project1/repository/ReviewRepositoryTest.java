package com.example.project1.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Test
    public void testInsert() {
        LongStream.rangeClosed(1, 10).forEach(i -> {

            Member member = Member.builder().mid(i).build();

            Review review = Review.builder()
                    .title("title..." + i)
                    .text("text..." + i)
                    .writer(member)
                    .build();
            reviewRepository.save(review);
            ReviewImage reviewImage = ReviewImage.builder()
                    .uuid(UUID.randomUUID().toString())
                    .imagename("img" + i + ".jpg")
                    .review(review)
                    .build();
            reviewImageRepository.save(reviewImage);
        });
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Object[]> list = reviewRepository.list("", "", pageable);
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

        }
    }
}
