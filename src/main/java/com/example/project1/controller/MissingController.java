package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.MissingDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.service.MissingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/missing")
public class MissingController {

    private final MissingService service;

    @GetMapping("/list")
    public void getReviewList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("missing controller 요청");

        PageResultDto<MissingDto, Object[]> result = service.getList(requestDto);
        log.info(result.getDtoList());
        model.addAttribute("result", result);
    }

    @GetMapping("/read")
    public void getRead(@RequestParam Long missno, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("missing read 요청 {}", missno);
        MissingDto result = service.getRow(missno);
        log.info(result);
        model.addAttribute("dto", result);

    }

    // @PreAuthorize("hasRole('MEMBER')")
    @GetMapping({ "/modify" })
    public void getModify(@RequestParam Long missno, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("missing read 요청 {}", missno);
        MissingDto result = service.getRow(missno);
        log.info(result);
        model.addAttribute("dto", result);
    }

    @PostMapping("/modify")
    public String postModify(@Valid MissingDto missingDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        if (result.hasErrors()) {
            return "/missing/modify";
        }

        Long missno = service.missingUpdate(missingDto);
        rttr.addFlashAttribute("msg", missno);

        rttr.addAttribute("missno", missingDto.getMissno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/missing/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long missno, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("remove 요청 {}", missno);
        service.missingRemove(missno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/missing/list";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/create")
    public void getCreate(MissingDto misingDto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("missing create 요청");

    }

    @PostMapping("/create")
    public String postRegister(@Valid MissingDto missingDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        if (result.hasErrors()) {
            return "/missing/create";
        }
        Long missno = service.missingInsert(missingDto);
        rttr.addFlashAttribute("msg", missno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/missing/list";
    }

}
