package com.example.project1.service;

import java.util.List;

import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.AnimalItem;

public interface AnimalCartService {

        // 장바구니 생성 + Item 추가
        void createCart(Long mid, Long sId, int count);

        // 장바구니 조회
        AnimalCart userCartView(Long mid);

        // 장바구니 찾기(컨트롤러)
        AnimalCart findByMember(Long mid);

        // 장바구니 item 삭제
        void cartItemDelete(Long itemId);

        // 찜목록
        List<AnimalItem> findItemsId(Long sId);

}
