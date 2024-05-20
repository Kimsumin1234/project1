package com.example.project1.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.service.UtilService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CoolSmsController {

    private final DefaultMessageService messageService;

    public CoolSmsController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSNJJ9FSXV2CGUH", "HUZV8WFKIJDXSCIZFQWPAN94LQQFG9TC",
                "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */
    @PostMapping("/send-one")
    public SingleMessageSentResponse sendOne() {
        log.info("문자메세지 호출");
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01063323055"); // 발신번호 입력
        message.setTo("01063323055"); // 수신번호 입력
        message.setText("[2팀] 본인확인\n" + "인증번호[" + randomNumbers(6) + "]를\n" + "화면에 입력해주세요.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }

    private String randomNumbers(int number) {
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