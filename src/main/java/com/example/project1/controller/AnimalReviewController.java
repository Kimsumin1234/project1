package com.example.project1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.AnimalReplyDto;
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
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public ResponseEntity<Long> postReply(@RequestBody AnimalReplyDto dto) {
        log.info("댓글 등록 {}", dto);

        return new ResponseEntity<Long>(animalReplyService.create(dto), HttpStatus.OK);
    }

    // /animalReviews/{rno} + DELETE
    @PreAuthorize("authentication.name == #email")
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno, String email) {

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
    @PreAuthorize("authentication.name == #replyDto.email")
    @PutMapping("/{rno}")
    public ResponseEntity<String> putReply(@PathVariable("rno") String rno, @RequestBody AnimalReplyDto replyDto) {
        log.info("댓글 수정 요청 {}, {}", rno, replyDto);

        animalReplyService.update(replyDto);

        return new ResponseEntity<String>(rno, HttpStatus.OK);
    }

}
