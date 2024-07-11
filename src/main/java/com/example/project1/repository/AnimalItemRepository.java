package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entity.AnimalItem;

public interface AnimalItemRepository extends JpaRepository<AnimalItem, Long> {

    @Query("select a from AnimalItem a where a.animalCart.cartId = :cartId and a.animal.sId = :sId")
    AnimalItem findByAnimalCartIdAndItemId(Long cartId, Long sId);

    // // 찜목록
    @Query("select a from AnimalItem a where a.animalCart.member.mid = :mid and a.animal.sId = :sId")
    AnimalItem findByAnimalMidAndItemId(Long mid, Long sId);

}
