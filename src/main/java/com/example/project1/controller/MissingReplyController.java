package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.MissingReplyDto;
import com.example.project1.service.MissingReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/missingreply")
@RequiredArgsConstructor
public class MissingReplyController {

    private final MissingReplyService replyService;

    // @PreAuthorize("permitAll()")
    @GetMapping("/{missno}/all")
    public ResponseEntity<List<MissingReplyDto>> getList(@PathVariable("missno") Long missno) {
        log.info("List<MissingReplyDto> {}", replyService.getList(missno));

        return new ResponseEntity<>(replyService.getList(missno), HttpStatus.OK);
    }

    @PostMapping("/{missno}")
    public ResponseEntity<Long> createReply(@RequestBody MissingReplyDto replyDto) {
        log.info("댓글 추가 요청 {}", replyDto);

        return new ResponseEntity<>(replyService.createReply(replyDto), HttpStatus.OK);
    }

    @DeleteMapping("/{missrno}")
    public ResponseEntity<String> removeReply(@PathVariable("missrno") Long missrno, String email) {
        // RequestBody 변수명 => review.js의 fetch함수 body의 변수명과 같음
        log.info("reply 삭제 {}", missrno);
        replyService.removeReply(missrno);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/{missrno}")
    public ResponseEntity<MissingReplyDto> getReply(@PathVariable("missrno") Long missrno) {
        log.info("reply 한개 읽어오기{}", missrno);

        return new ResponseEntity<>(replyService.getReply(missrno), HttpStatus.OK);
    }

    @PutMapping("/{missrno}")
    public ResponseEntity<Long> modifyReply(@PathVariable("missrno") String missrno,
            @RequestBody MissingReplyDto replyDto) {

        return new ResponseEntity<>(replyService.updateReply(replyDto), HttpStatus.OK);
    }
}
