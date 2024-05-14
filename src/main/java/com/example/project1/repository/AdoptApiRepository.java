package com.example.project1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.AdoptApi;

public interface AdoptApiRepository extends JpaRepository<AdoptApi, Long> {

    @Query(value = "SELECT * FROM ANIMAL a", nativeQuery = true)
    Page<Object[]> getListPage(Pageable pageable);

}
