package com.example.project1.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.AnimalHeart;
import com.example.project1.entity.AnimalItem;
import com.example.project1.entity.AnimalReply;
import com.example.project1.entity.Member;
import com.example.project1.service.AnimalCartServiceImpl;
import com.example.project1.service.AnimalHeartServiceImpl;

@SpringBootTest
public class AnimalCartRepositoryTest {

    @Autowired
    private AnimalCartRepository animalCartRepository;

    @Autowired
    private AnimalItemRepository animalItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnimalCartServiceImpl animalCartService;

    @Autowired
    private AnimalHeartRepository heartRepository;

    // @Test
    // public void createCarttTest() {

    // // 카트 저장(소유주 저장)
    // // Member member = new Member();
    // // member.setMid(39L);
    // // memberRepository.save(member);

    // // cart 가 존재하는지 찾는 코드 필요
    // // AnimalCart animalCart = animalCartRepository.findById(1L).get();
    // // cart 만들기
    // // AnimalCart animalCart =
    // // animalCartRepository.save(AnimalCart.builder().member(member).build());

    // // cart 랑 연동된 아이템 추가
    // // Animal animal = Animal.builder().sId(4L).build();
    // // AnimalItem animalItem =
    // // AnimalItem.builder().animal(animal).animalCart(animalCart).build();
    // // animalItemRepository.save(animalItem);

    // animalCartService.createCart(39L, 13L, 0);

    // }

    // @Transactional
    // @Test
    // public void cartRead() {

    // // 멤버의 카트 찾기
    // // AnimalCart cart = animalCartRepository.findByMember(39L);

    // // 찾은 카트와 연동된 아이템 찾기
    // // System.out.println(cart);
    // // cart.getAnimalItems().forEach(item -> {
    // // System.out.println(item.getAnimal().getKindCd());
    // // System.out.println(item.getAnimal().getFilename());

    // // });

    // AnimalCart cart = animalCartService.userCartView(40L);
    // cart.getAnimalItems().forEach(item -> {
    // System.out.println(item.getAnimal().getKindCd());
    // System.out.println(item.getAnimal().getFilename());
    // });
    // }

    // 하트

    // @Test
    // public void addHeart() {
    // Member member = Member.builder().mid(40L).build();
    // Animal animal = Animal.builder().sId(1000L).build();

    // heartRepository.save(AnimalHeart.builder().member(member).animal(animal).build());
    // }

}
