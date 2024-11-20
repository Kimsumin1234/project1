package com.example.project1.repository;

import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;
import com.example.project1.repository.missing.MissingMemberReviewRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MissingImageRepository
  extends JpaRepository<Missingimage, Long>, MissingMemberReviewRepository {
  @Modifying
  @Query("delete from Missingimage mi where mi.missing = :missing")
  void deleteByMissing(Missing missing);

  // 어제 날짜 이미지 가져오기 (디비버에서 쿼리문 작성함)
  @Query(
    value = "SELECT * FROM MISSINGIMAGE mi WHERE mi.PATH = TO_CHAR(SYSDATE -1,'yyyy\\mm\\dd')",
    nativeQuery = true
  )
  List<Missingimage> getOldMissingImages();
}
