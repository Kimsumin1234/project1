package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.HeartDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.service.HeartService;
import com.example.project1.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;
    private final ReviewService reviewService;

    @GetMapping("/{rno}")
    public ResponseEntity<Long> getMethodName(@PathVariable("rno") Long rno) {
        log.info("rno {}", rno);
        return new ResponseEntity<>(heartService.reviewHeart(rno), HttpStatus.OK);
    }

    @GetMapping("/{mid}/{rno}")
    public ResponseEntity<HeartDto> getHeart(@PathVariable("mid") Long mid, @PathVariable("rno") Long rno) {
        log.info(mid);
        log.info(rno);
        HeartDto heartDto = heartService.getHeart(mid, rno);

        log.info(heartDto);
        return new ResponseEntity<>(heartDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> postAddHeart(@RequestBody HeartDto heartDto) {
        log.info(heartDto);
        heartService.addHeart(heartDto);
        return new ResponseEntity<>(heartService.dtoToEntity(heartDto).getHno(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteHeart(@RequestBody HeartDto heartDto) {
        log.info(heartDto);
        heartService.deleteHeart(heartDto);
        return new ResponseEntity<>(heartService.dtoToEntity(heartDto).getHno(), HttpStatus.OK);
    }

    @GetMapping("/{mid}/heartList")
    public ResponseEntity<List<ReviewDto>> getheartReviewList(@PathVariable("mid") Long mid) {
        log.info(mid);
        List<ReviewDto> reviewDtos = reviewService.getHeartList(mid);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

}
