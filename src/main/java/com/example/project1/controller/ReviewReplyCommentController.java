package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.ReviewReplyCommentDto;
import com.example.project1.service.ReviewReplyCommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

}
