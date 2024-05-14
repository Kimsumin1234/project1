package com.example.project1.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 어느 엔티티와 연결될지 알려주기 위해 파일이름에 엔티티 이름을 다 써줌
public interface ReviewMemberReviewReplyRepository {
    // @Query(select m, t form member m join m,team t = ?1) select 해서 나오는 테이블이 2개
    // 이상일때 받을 수 있는 타입 Object
    // 전체 조회 시 review, member, reply 정보 다 조회
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    Object[] getRow(Long rno);
}
