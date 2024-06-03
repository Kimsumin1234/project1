package com.example.project1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.AnimalHeartDto;
import com.example.project1.service.AnimalHeartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/animalHeart")
public class AnimalHeartController {

    private final AnimalHeartService animalHeartService;

    // 게시물에 해당하는 하트
    @GetMapping("/{sId}")
    public ResponseEntity<Long> getMethodName(@PathVariable("sId") Long sId) {
        log.info("sId {}", sId);
        return new ResponseEntity<>(animalHeartService.animalHeart(sId),
                HttpStatus.OK);
    }

    // 멤버가 누른 게시물에 해당하는 하트
    @GetMapping("/{mid}/{sId}")
    public ResponseEntity<AnimalHeartDto> getHeart(@PathVariable("mid") Long mid,
            @PathVariable("sId") Long sId) {
        log.info("하트 가져오기 {}", mid);
        log.info("하트 가져오기 {}", sId);
        AnimalHeartDto heartDto = animalHeartService.getHeart(mid, sId);

        log.info(heartDto);
        return new ResponseEntity<>(heartDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/add")
    public ResponseEntity<Long> postAddHeart(@RequestBody AnimalHeartDto heartDto) {
        log.info("하트추가 {} ", heartDto);
        animalHeartService.addHeart(heartDto);
        return new ResponseEntity<>(animalHeartService.dtoToEntity(heartDto).getHid(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteHeart(@RequestBody AnimalHeartDto heartDto) {
        log.info(heartDto);
        animalHeartService.deleteHeart(heartDto);
        return new ResponseEntity<>(animalHeartService.dtoToEntity(heartDto).getHid(), HttpStatus.OK);
    }

}
