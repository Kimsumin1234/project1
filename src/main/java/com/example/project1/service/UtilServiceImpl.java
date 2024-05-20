package com.example.project1.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public String randomNumbers(int number) {
        log.info("랜덤번호 {} 자리 생성", number);

        Random random = new Random();
        String numbers = "";

        for (int i = 0; i < number; i++) {
            String rNum = Integer.toString(random.nextInt(10));

            numbers += rNum;
        }

        return numbers;
    }

}
