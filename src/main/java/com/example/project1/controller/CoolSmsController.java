package com.example.project1.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.CertificationDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.service.UtilService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice
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
    public SingleMessageSentResponse sendOne(@Valid CertificationDto cDto, MemberDto memberDto, HttpSession session) {
        log.info("문자메세지 호출 {} {}", cDto, memberDto);

        String rNum = randomNumbers(6);

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01063323055"); // 발신번호 입력
        message.setTo(cDto.getPhone()); // 수신번호 입력
        message.setText("[2팀] 본인확인\n" + "인증번호[" + rNum + "]를\n" + "화면에 입력해주세요.");

        session.setAttribute("rNum", rNum);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
        System.out.println(response.getTo());
        // memberDto.setPhone(response.getTo());
        log.info("session {}", session.getAttribute("rNum"));

        return response;
    }

    @PostMapping("/certif")
    public ResponseEntity<String> postMethodName(CertificationDto cDto, HttpSession session) {
        log.info("인증번호 확인 요청 {} {}", cDto, session.getAttribute("rNum"));

        if (!cDto.getCertNum().equals(session.getAttribute("rNum"))) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    // @@ExceptionHandler(Exception) : 해당 Exception 이 나면 메소드가 실행되는 어노테이션
    // Valid 검증이 실패한 경우의 메소드
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // key,value 형태로 담기위해서 Map 을 사용
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
