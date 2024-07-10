package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.entity.Chart;
import com.example.project1.repository.ChartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/chart")
public class ChartRestController {

    private final ChartRepository chartRepository;

    @GetMapping("/list/{keyword}")
    public ResponseEntity<List<Chart>> getList(@PathVariable("keyword") String keyword) {
        log.info("리스트 요청", keyword);

        return new ResponseEntity<>(chartRepository.findByCareAddrContaining(keyword), HttpStatus.OK);
    }

}
