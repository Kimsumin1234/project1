package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;
import com.example.project1.repository.missing.MissingMemberReviewRepository;

public interface MissingImageRepository
        extends JpaRepository<Missingimage, Long>, MissingMemberReviewRepository {

    @Modifying
    @Query("delete from Missingimage mi where mi.missing = :missing")
    void deleteByMissing(Missing missing);

}
