package com.example.project1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.AnimalReplyDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.service.AnimalReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RestController
@RequestMapping("/animalReviews")
@RequiredArgsConstructor
public class AnimalReviewController {

    private final AnimalReplyService animalReplyService;

    @GetMapping("/{sId}/all")
    public ResponseEntity<List<AnimalReplyDto>> getReviews(@PathVariable("sId") Long sId) {

        return new ResponseEntity<>(animalReplyService.getReplies(sId), HttpStatus.OK);
    }

    // /animalReviews/new + POST
    @PostMapping("/new")
    public ResponseEntity<Long> postReply(@RequestBody AnimalReplyDto dto) {
        log.info("댓글 등록 {}", dto);

        return new ResponseEntity<Long>(animalReplyService.create(dto), HttpStatus.OK);
    }

    // /animalReviews/{rno} + DELETE
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno) {

        log.info("댓글 제거 {}", rno);
        animalReplyService.remove(rno);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // /animalReviews/{rno} + GET
    @GetMapping("/{rno}")
    public ResponseEntity<AnimalReplyDto> getRow(@PathVariable("rno") Long rno) {
        log.info("댓글 하나 요청 {}", rno);
        return new ResponseEntity<AnimalReplyDto>(animalReplyService.getReply(rno), HttpStatus.OK);
    }

    // /animalReviews/{rno} + PUT
    @PutMapping("/{id}")
    public ResponseEntity<String> putReply(@PathVariable("id") String id, @RequestBody AnimalReplyDto replyDto) {
        log.info("댓글 수정 요청 {}, {}", id, replyDto);

        Long rno = animalReplyService.update(replyDto);

        return new ResponseEntity<String>(String.valueOf(rno), HttpStatus.OK);
    }

}
