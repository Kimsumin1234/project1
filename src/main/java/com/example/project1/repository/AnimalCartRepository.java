package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.Review;

public interface AnimalCartRepository extends JpaRepository<AnimalCart, Long> {

    @Query("select a from AnimalCart a where a.member.mid = :mid")
    AnimalCart findByMember(Long mid);

}
