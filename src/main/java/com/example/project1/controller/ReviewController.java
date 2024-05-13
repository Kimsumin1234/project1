package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequestMapping("/review")
public class ReviewController {

    @GetMapping("/list")
    public void getReviewList() {
        log.info("review controller 요청");
    }

}
