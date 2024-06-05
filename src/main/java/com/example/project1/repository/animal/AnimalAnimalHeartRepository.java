package com.example.project1.repository.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 어느 엔티티와 연결될지 알려주기 위해 파일이름에 엔티티 이름을 다 써줌
public interface AnimalAnimalHeartRepository {
    Page<Object[]> list(String type, String keyword, Pageable pageable);
}
