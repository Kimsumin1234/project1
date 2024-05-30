package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Heart;
import com.example.project1.entity.Review;
import com.example.project1.entity.Member;
import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByMemberAndReview(Member member, Review review);

    List<Heart> findByReview(Review review);

    List<Heart> findByMember(Member member);

    void deleteByReview(Review review);

}
