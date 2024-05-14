package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @GetMapping("/read")
    public void getRead(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("review read 요청");
    }

}
