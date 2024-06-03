package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.service.ReviewService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.ruleEntry_return;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

    @GetMapping("/read")
    public String getincrementViewCount(@RequestParam Long rno, HttpServletRequest request, Model model,
            HttpServletResponse response, @ModelAttribute("requestDto") PageRequestDto requestDto) {

        Cookie oldCookie = null;
        // Check if the user has already viewed this page
        Cookie[] cookies = request.getCookies();
        log.info(cookies);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewed_" + rno)) {
                    log.info(cookie.getName());
                    oldCookie = cookie;
                    break;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + rno.toString() + "]")) {
                service.incrementViewCount(rno);
                oldCookie.setValue(oldCookie.getValue() + "_[" + rno + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            service.incrementViewCount(rno);
            Cookie newCookie = new Cookie("viewed_" + rno, "[" + rno + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }

        log.info("review read 요청 {}", rno);
        ReviewDto result = service.getRow(rno);
        log.info(result);
        model.addAttribute("dto", result);

        return "/review/read";

    }

    @GetMapping({ "/modify" })
    public void getRead(@RequestParam Long rno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("review read 요청 {}", rno);
        ReviewDto result = service.getRow(rno);
        log.info(result);
        model.addAttribute("dto", result);
    }

    @PostMapping("/modify")
    public String postModify(@Valid ReviewDto reviewDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        if (result.hasErrors()) {
            return "/review/modify";
        }

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
    public void getCreate(ReviewDto reviewDto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("review create 요청");

    }

    @PostMapping("/register")
    public String postRegister(@Valid ReviewDto reviewDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        if (result.hasErrors()) {
            return "/review/register";
        }

        if (reviewDto.getViewCount() == null) {
            reviewDto.setViewCount(0L);
        }
        Long rno = service.reviewInsert(reviewDto);
        rttr.addFlashAttribute("msg", rno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/review/list";
    }

}
