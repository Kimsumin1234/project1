package com.example.project1.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project1.entity.Member;
import com.example.project1.entity.Review;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

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
        });
    }

}
