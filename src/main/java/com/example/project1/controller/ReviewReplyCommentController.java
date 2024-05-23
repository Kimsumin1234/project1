package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.ReviewReplyCommentDto;
import com.example.project1.dto.ReviewReplyDto;
import com.example.project1.service.ReviewReplyCommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class ReviewReplyCommentController {

    private final ReviewReplyCommentService commentService;

    @GetMapping("/{replyNo}/all")
    public ResponseEntity<List<ReviewReplyCommentDto>> getList(@PathVariable("replyNo") Long replyNo) {
        log.info("comment controller 요청 {}", replyNo);
        log.info("List<ReviewReplyCommentDto> {}", commentService.getListOfComment(replyNo));

        return new ResponseEntity<>(commentService.getListOfComment(replyNo), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> postAddReply(
            @RequestBody ReviewReplyCommentDto commentDto) {
        log.info("대댓글 추가 요청 {}", commentDto);

        return new ResponseEntity<>(commentService.addComment(commentDto), HttpStatus.OK);
    }

    @GetMapping("/{replyNo}/{commentNo}")
    public ResponseEntity<ReviewReplyCommentDto> getComment(@PathVariable("replyNo") Long replyNo,
            @PathVariable("commentNo") Long commentNo) {
        log.info("대댓글 수정 요청 {}", commentNo);
        return new ResponseEntity<>(commentService.getComment(commentNo), HttpStatus.OK);
    }

}
