package com.example.project1.controller;

import com.example.project1.dto.CertificationDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.service.AdoptUserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@ControllerAdvice
@RestController
public class CoolSmsController {

  private DefaultMessageService messageService;

  @Autowired
  private AdoptUserService adoptUserService;

  @Value("${api.key}")
  private String apiKey;

  @Value("${api.secret}")
  private String apiSecret;

  @PostConstruct
  public void init() {
    this.messageService =
      NurigoApp.INSTANCE.initialize(
        apiKey,
        apiSecret,
        "https://api.coolsms.co.kr"
      );
  }

  /**
   * 단일 메시지 발송 예제
   */
  @PostMapping("/send-one")
  public SingleMessageSentResponse sendOne(
    @Valid CertificationDto cDto,
    MemberDto memberDto,
    HttpSession session
  ) {
    log.info("문자메세지 호출 {} {}", cDto, memberDto);

    // try {
    // adoptUserService.validateDuplicationMemberPhone(cDto.getPhone());
    // } catch (IllegalStateException e) {
    // session.setAttribute("DupliPhone", e.getMessage());
    // // session.invalidate();
    // return new ResponseEntity<SingleMessageSentResponse>(HttpStatus.BAD_REQUEST);
    // }

    adoptUserService.validateDuplicationMemberPhone(cDto.getPhone());

    String rNum = randomNumbers(6);

    Message message = new Message();
    // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
    message.setFrom("01063323055"); // 발신번호 입력
    message.setTo(cDto.getPhone()); // 수신번호 입력
    // message.setTo("01063323055"); // 수신번호 입력
    message.setText(
      "[Saving Paws] 본인확인\n" +
      "인증번호[" +
      rNum +
      "]를\n" +
      "화면에 입력해주세요."
    );

    session.setAttribute("rNum", rNum);

    SingleMessageSentResponse response =
      this.messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(response);
    System.out.println(response.getTo());
    // memberDto.setPhone(response.getTo());
    log.info("session {}", session.getAttribute("rNum"));

    return response;
  }

  @PostMapping("/send-one2")
  public SingleMessageSentResponse sendOne2(
    @Valid CertificationDto cDto,
    MemberDto memberDto,
    HttpSession session
  ) {
    log.info("문자메세지 호출 {} {}", cDto, memberDto);

    MemberDto finDto = adoptUserService.findId(cDto.getPhone());

    String rNum = randomNumbers(6);

    Message message = new Message();
    message.setFrom("01063323055");
    message.setTo(cDto.getPhone());
    message.setText(
      "[Saving Paws] 본인확인\n" +
      "인증번호[" +
      rNum +
      "]를\n" +
      "화면에 입력해주세요."
    );

    session.setAttribute("rNum", rNum);
    session.setAttribute("findId", finDto.getEmail());

    SingleMessageSentResponse response =
      this.messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(response);
    System.out.println(response.getTo());

    log.info("session rNum : {}", session.getAttribute("rNum"));
    log.info("session findId : {}", session.getAttribute("findId"));

    return response;
  }

  @PostMapping("/send-one3")
  public SingleMessageSentResponse sendOne3(
    @Valid CertificationDto cDto,
    MemberDto memberDto,
    HttpSession session
  ) {
    log.info("문자메세지 호출 {} {}", cDto, memberDto);

    adoptUserService.equalPhoneEmail(cDto.getPhone(), memberDto.getEmail());

    String rNum = randomNumbers(6);

    Message message = new Message();
    message.setFrom("01063323055");
    message.setTo(cDto.getPhone());
    message.setText(
      "[Saving Paws] 본인확인\n" +
      "인증번호[" +
      rNum +
      "]를\n" +
      "화면에 입력해주세요."
    );

    session.setAttribute("rNum", rNum);
    // session.setAttribute("findId", finDto.getEmail());

    SingleMessageSentResponse response =
      this.messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(response);
    System.out.println(response.getTo());

    log.info("session rNum : {}", session.getAttribute("rNum"));
    // log.info("session findId : {}", session.getAttribute("findId"));

    return response;
  }

  @PostMapping("/certif")
  public ResponseEntity<String> postMethodName(
    CertificationDto cDto,
    HttpSession session
  ) {
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
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
    MethodArgumentNotValidException ex
  ) {
    // key,value 형태로 담기위해서 Map 을 사용
    Map<String, String> errors = new HashMap<>();

    ex
      .getBindingResult()
      .getFieldErrors()
      .forEach(error -> {
        String fieldName = error.getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
    IllegalStateException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    String fieldName = "duplierror";
    String errorMessage = ex.getMessage();
    errors.put(fieldName, errorMessage);
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
