package com.example.project1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.project1.dto.AnimalCartDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.AnimalItem;
import com.example.project1.entity.Member;
import com.example.project1.repository.AnimalCartRepository;
import com.example.project1.repository.AnimalItemRepository;
import com.example.project1.repository.AnimalRepository;
import com.example.project1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AnimalCartServiceImpl implements AnimalCartService {

    private final AnimalCartRepository animalCartRepository;
    private final AnimalItemRepository animalItemRepository;
    private final MemberRepository memberRepository;
    private final AnimalRepository animalRepository;

    // 찜목록
    @Override
    public AnimalItem findItemsByCartIdAndSId(Long sId, Long mid) {
        log.info("찜목록 확인: sId {}", sId);
        // Cart에 있는 Member 조회
        // AnimalCart cart = animalCartRepository.findByMember(mid);
        return animalItemRepository.findByAnimalMidAndItemId(mid, sId);
    }

    // 장바구니 생성과 아이템 추가
    @Override
    public void createCart(Long mid, Long sId, int count) {

        // MemberId 조회
        Member cartMember = memberRepository.findByMid(mid).get();
        // Cart에 있는 Member 조회
        AnimalCart cart = animalCartRepository.findByMember(mid);
        // Item 조회
        Animal animal = animalRepository.findById(sId).get();

        // cart가 존재하는지 확인하는 코드 만들기
        if (cart == null) {
            cart = AnimalCart.createCart(cartMember);
            animalCartRepository.save(cart);
        }

        // 장바구니 안에 장바구니 상품 조회
        AnimalItem animalItem = animalItemRepository.findByAnimalCartIdAndItemId(cart.getCartId(), animal.getSId());

        // item 비어있다면 생성
        if (animalItem == null) {
            animalItem = AnimalItem.createCartItem(count, cart, animal);
            animalItemRepository.save(animalItem);
            // AnimalItem saveItem = animalItemRepository.save(animalItem);
            // cart.setCount(cart.getCount() + 1);
        } else {
            // 같은 sId면 추가하지 않음
            if (animalItem.getAnimal().getSId().equals(animal.getSId())) {
                log.info(animalItem.getAnimal().getSId());
                log.info("이미 문의신청한 게시물입니다.");
                return;
            }
        }
    }

    // 장바구니 조회
    @Override
    public AnimalCart userCartView(Long mid) {
        log.info("장바구니 조회 service {}", mid);
        AnimalCart cart = animalCartRepository.findByMember(mid);

        return cart;
    }

    // 장바구니 item 삭제
    @Override
    public void cartItemDelete(Long itemId) {
        animalItemRepository.deleteById(itemId);
    }

    @Override
    public AnimalCart findByMember(Long mid) {
        return animalCartRepository.findByMember(mid);
    }

}
