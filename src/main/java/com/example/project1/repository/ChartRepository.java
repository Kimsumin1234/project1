package com.example.project1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Chart;

public interface ChartRepository extends JpaRepository<Chart, Long> {

    // 보호장소로 리스트 가져오기
    List<Chart> findByCareAddrContaining(String keyword);

    // 보호장소로 리스트 가져오기
    List<Chart> findByCareAddrContainingAndHappenDtBetween(String keyword, LocalDate start, LocalDate end);
}
