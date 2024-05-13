package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Review;
import com.example.project1.repository.search.SearchReviewRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, SearchReviewRepository {

}
