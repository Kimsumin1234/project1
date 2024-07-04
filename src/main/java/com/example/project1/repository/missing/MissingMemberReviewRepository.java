package com.example.project1.repository.missing;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MissingMemberReviewRepository {

    Page<Object[]> list(String type, String keyword, Pageable pageable);

    List<Object[]> getRow(Long missno);

}
