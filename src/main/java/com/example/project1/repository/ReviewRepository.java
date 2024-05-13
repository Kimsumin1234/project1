package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
