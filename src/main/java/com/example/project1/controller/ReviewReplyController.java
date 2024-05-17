package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.service.ReviewReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReviewReplyController {

    private final ReviewReplyService replyService;

    @GetMapping("/{rno}/all")
    public ResponseEntity<List<ReviewReplyDto>> getList(@PathVariable("rno") Long rno) {
        log.info("reply controller 요청 {}", rno);
        return new ResponseEntity<>(replyService.getListOfReview(rno), HttpStatus.OK);
    }

    @PostMapping("/{rno}")
    public ResponseEntity<Long> postAddReply(@PathVariable("rno") Long rno, @RequestBody ReviewReplyDto replyDto) {
        log.info("댓글 추가 요청 {}", rno);

        return new ResponseEntity<>(replyService.addReply(replyDto), HttpStatus.OK);
    }

}
