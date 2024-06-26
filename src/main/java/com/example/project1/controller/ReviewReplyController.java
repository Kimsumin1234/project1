package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.ReviewDto;
import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.service.ReviewReplyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReviewReplyController {

    private final ReviewReplyService replyService;

    @PreAuthorize("permitAll()")
    @GetMapping("/{rno}/all")
    public ResponseEntity<List<ReviewReplyDto>> getList(@PathVariable("rno") Long rno) {
        log.info("reply controller 요청 {}", rno);
        log.info("List<ReviewReplyDto> {}", replyService.getListOfReview(rno));

        return new ResponseEntity<>(replyService.getListOfReview(rno), HttpStatus.OK);
    }

    @PostMapping("/{rno}")
    public ResponseEntity<Long> postAddReply(@PathVariable("rno") Long rno,
            @RequestBody ReviewReplyDto replyDto) {
        log.info("댓글 추가 요청 {}", rno);

        return new ResponseEntity<>(replyService.addReply(replyDto), HttpStatus.OK);
    }

    @DeleteMapping("/{rno}/{replyNo}")
    public ResponseEntity<Long> deleteReview(@PathVariable("replyNo") Long replyNo, String email) {
        // RequestBody 변수명 => review.js의 fetch함수 body의 변수명과 같음
        log.info("reply 삭제 {}", replyNo);
        replyService.removeReply(replyNo);
        return new ResponseEntity<>(replyNo, HttpStatus.OK);
    }

    @GetMapping("/{rno}/{replyNo}")
    public ResponseEntity<ReviewReplyDto> getReview(@PathVariable("replyNo") Long replyNo) {
        log.info("reply 한개 읽어오기{}", replyNo);

        return new ResponseEntity<>(replyService.getReply(replyNo), HttpStatus.OK);
    }

    @PutMapping("/{rno}/{replyNo}")
    public ResponseEntity<Long> putReview(@PathVariable("replyNo") Long replyNo,
            @RequestBody ReviewReplyDto replyDto) {
        log.info("put controller reply 수정 {}", replyDto);

        return new ResponseEntity<>(replyService.updateReply(replyDto), HttpStatus.OK);
    }
}
