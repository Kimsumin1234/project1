package com.example.project1.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.project1.dto.AnimalCartDto;
import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.AnimalItemDto;
import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.entity.AnimalCart;
import com.example.project1.entity.AnimalItem;
import com.example.project1.entity.Member;
import com.example.project1.service.AnimalCartService;

import jakarta.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class AnimalCartController {

    private final AnimalCartService animalCartService;

    // 장바구니 조회 (RestController 는 Model를 담을 수 없음)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/animalCarts")
    public void cartView(Long mid, HttpServletRequest request,
            Model model) {

        MemberDto member = getMemberFromSession();
        log.info("장바구니 조회- 세션 {}", member.getMid());

        AnimalCart cart = animalCartService.findByMember(member.getMid());
        log.info("cart {}", cart);
        if (cart != null) {
            List<AnimalItem> cartItems = cart.getAnimalItems();

            log.info("size {}", cartItems.size());
            log.info("======================================");
            log.info(cartItems);

            model.addAttribute("cartItems", cartItems);
        } else {
            model.addAttribute("cartItems", List.of());
        }

    }

    // 장바구니에 추가
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public String addCart(AnimalDto dto) {
        log.info("장바구니 만들기");
        log.info("getSId {}", dto.getSId());

        MemberDto member = getMemberFromSession();
        animalCartService.createCart(member.getMid(), dto.getSId(), 1);
        // log.info("count {}", count);
        return "redirect:/cart/animalCarts";
    }

    // 장바구니 아이템 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String postDelete(@RequestParam Long itemId) {

        animalCartService.cartItemDelete(itemId);
        log.info("itemId {}", itemId);
        return "redirect:/cart/animalCarts";
    }

    // 현재 로그인 되어있는 멤버정보 가져오기
    private MemberDto getMemberFromSession() {
        log.info("세션 가져오기");
        // 세션에서 SecurityContext 가져오기
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 사용자 정보 가져오기 (여기서는 AuthMemberDto를 사용)
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();

        // AuthMemberDto를 MemberDto로 변환
        MemberDto memberDto = authMemberDto.getMemberDto();

        log.info("세션 memberDto {}", memberDto);

        return memberDto;
    }

}