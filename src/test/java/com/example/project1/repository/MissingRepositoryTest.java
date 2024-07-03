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
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.entity.Heart;
import com.example.project1.entity.Member;
import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.entity.ReviewReply;
import com.example.project1.entity.ReviewReplyComment;

@SpringBootTest
public class MissingRepositoryTest {
    @Autowired
    private MissingRepository missingRepository;
    @Autowired
    private MissingImageRepository missingImageRepository;
    @Autowired
    private ReviewReplyRepository replyRepository;
    @Autowired
    private ReviewReplyCommentRepository commentRepository;
    @Autowired
    private HeartRepository heartRepository;

    @Test
    public void testInsert() {
        LongStream.rangeClosed(1, 5).forEach(i -> {

            Member member = Member.builder().mid(i).build();

            Missing missing = Missing.builder()
                    .title("title..." + i)
                    .text("text..." + i)
                    .member(member)
                    .build();
            missingRepository.save(missing);
            Missingimage missingImage = Missingimage.builder()
                    .missing(missing)
                    .build();
            missingImageRepository.save(missingImage);
        });
    }

    // @Test
    // public void testInsertReply() {
    // LongStream.rangeClosed(1, 10).forEach(i -> {
    // Member member = Member.builder().mid(i).build();
    // Review review = Review.builder().rno(i).build();
    // ReviewReply reviewReply = ReviewReply.builder()
    // .text("test..." + i)
    // .replyer(member)
    // .review(review)
    // .build();
    // replyRepository.save(reviewReply);
    // });
    // }

    // @Test
    // public void testInsertComment() {
    // Member member = Member.builder().mid(1L).build();
    // ReviewReply reply = ReviewReply.builder().replyNo(1L).build();
    // ReviewReplyComment comment = ReviewReplyComment.builder()
    // .replyer(member)
    // .reply(reply)
    // .text("대댓글 테스트22")
    // .build();
    // commentRepository.save(comment);
    // }

    // @Test
    // public void testList() {
    // Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

    // Page<Object[]> list = reviewRepository.list("", "", pageable);
    // for (Object[] objects : list) {
    // System.out.println(Arrays.toString(objects));

    // }
    // }

    @Test
    public void testRow() {
        List<Object[]> result = missingImageRepository.getRow(1L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // @Transactional
    // @Test
    // public void testReplyComment() {
    // List<ReviewReply> list = reviewRepository.getReviewReplies(1L);

    // list.forEach(r -> {
    // System.out.println(r);
    // System.out.println();
    // System.out.println(r.getReplyComment());
    // });
    // }

    // @Test
    // public void testS() {
    // Member member = Member.builder().mid(1L).build();
    // List<Heart> hearts = heartRepository.findByMember(member);
    // for (Heart heart : hearts) {
    // reviewRepository.findByHeart(heart.getHno());
    // }

    // }
}
