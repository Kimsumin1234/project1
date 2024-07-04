package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Animal;
import com.example.project1.service.AdoptApiService;
import com.example.project1.service.AnimalHeartService;
import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.PageRequestDto;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/adopt")
public class AnimalController {

    private final AdoptApiService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("입양 리스트 페이지 요청");

        PageResultDto<AnimalDto, Object[]> result = service.getList(requestDto);

        System.out.println("list" + result.getDtoList());

        model.addAttribute("result", result);

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/read")
    public void getRead(@RequestParam Long sId, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read 요청");

        model.addAttribute("dto", service.getRow(sId));
    }
}
