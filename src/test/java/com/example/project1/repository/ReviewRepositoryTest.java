package com.example.project1.repository;

import java.util.Arrays;
import java.util.List;
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
import com.example.project1.entity.ReviewReply;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private ReviewReplyRepository replyRepository;

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
    public void testInsertReply() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder().mid(i).build();
            Review review = Review.builder().rno(i).build();
            ReviewReply reviewReply = ReviewReply.builder()
                    .text("test..." + i)
                    .replyer(member)
                    .review(review)
                    .build();
            replyRepository.save(reviewReply);
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

    @Test
    public void testeRow() {
        List<Object[]> result = reviewImageRepository.getRow(1L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }
}
