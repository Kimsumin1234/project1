package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService service;

    @GetMapping("/list")
    public void getReviewList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("review controller 요청");

        PageResultDto<ReviewDto, Object[]> result = service.getList(requestDto);
        log.info(result.getDtoList());
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long rno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("review read 요청 {}", rno);
        ReviewDto result = service.getRow(rno);
        log.info(result);
        model.addAttribute("dto", result);
    }

    @PostMapping("/modify")
    public String postModify(ReviewDto reviewDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("modify post controller 요청 {}", reviewDto);
        Long rno = service.reviewUpdate(reviewDto);
        rttr.addFlashAttribute("msg", rno);

        rttr.addAttribute("rno", reviewDto.getRno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/review/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long rno, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("remove 요청 {}", rno);
        service.reviewRemove(rno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/review/list";
    }

    @GetMapping("/register")
    public void getCreate(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("review create 요청");

    }

    @PostMapping("/register")
    public String postRegister(ReviewDto reviewDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        Long rno = service.reviewInsert(reviewDto);
        rttr.addFlashAttribute("msg", rno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/review/list";
    }

}
