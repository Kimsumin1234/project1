package com.example.project1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.constant.MemberRole;
import com.example.project1.dto.AuthMemberDto;
import com.example.project1.dto.FindPasswordDto;
import com.example.project1.dto.MemberDto;
import com.example.project1.dto.NicknameChangeDto;
import com.example.project1.dto.PasswordChangeDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.ReviewReply;
import com.example.project1.repository.HeartRepository;
import com.example.project1.repository.MemberRepository;
import com.example.project1.repository.ReviewReplyCommentRepository;
import com.example.project1.repository.ReviewReplyRepository;
import com.example.project1.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AdoptUserServiceImpl implements UserDetailsService, AdoptUserService {

    private final MemberRepository memberRepository;

    private final HeartRepository heartRepository;

    private final ReviewReplyCommentRepository reviewReplyCommentRepository;

    private final ReviewReplyRepository reviewReplyRepository;

    private final ReviewRepository reviewRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 메소드
        log.info("로그인 요청 {}", username);

        Optional<Member> result = memberRepository.findByEmailAndFromSocial(username, false);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }
        Member member = result.get();

        return new AuthMemberDto(entityToDto(member), false);
    }

    @Transactional
    @Override
    public String nickNameUpdate(NicknameChangeDto nicknameChangeDto) throws IllegalStateException {
        log.info("닉네임 수정 ServiceImpl {}", nicknameChangeDto);

        // 중복 검사
        validateDuplicationMemberNickName(nicknameChangeDto.getNickname());

        memberRepository.updateNickName(nicknameChangeDto.getNickname(), nicknameChangeDto.getEmail());
        return "닉네임 수정 완료";
    }

    @Override
    public void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException {
        log.info("비밀번호 수정 service {}", pDto);

        Member member = memberRepository.findByEmailAndFromSocial(pDto.getEmail(), false).get();

        if (!passwordEncoder.matches(pDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다.");
        } else {
            member.setPassword(passwordEncoder.encode(pDto.getNewPassword()));
            memberRepository.save(member);
        }
    }

    // 닉네임 중복 확인
    private void validateDuplicationMemberNickName(String nickname) throws IllegalStateException {
        log.info("닉네임 중복확인 {}", nickname);
        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.isPresent()) {
            throw new IllegalStateException("중복된 닉네임 입니다.");
        }
    }

    @Transactional
    @Override
    public String leave(MemberDto leaveMemberDto) throws IllegalStateException {
        log.info("회원탈퇴 service {}", leaveMemberDto);

        // 아이디 와 비밀번호 일치 시
        Member member = memberRepository.findByEmail(leaveMemberDto.getEmail()).get();
        if (!passwordEncoder.matches(leaveMemberDto.getPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다.");

        } else {
            heartRepository.deleteByMember(member);
            reviewReplyCommentRepository.deleteByMember(member);
            reviewReplyRepository.deleteByMember(member);
            reviewRepository.deleteByMember(member);
            memberRepository.delete(member);
        }

        return "회원탈퇴 완료";
    }

    @Override
    public String register(MemberDto insertDto) throws IllegalStateException {
        log.info("회원가입 srvice {}", insertDto);

        // 중복 검사
        validateDuplicationMemberEmail(insertDto.getEmail());
        validateDuplicationMemberNickName(insertDto.getNickname());

        Member member = Member.builder()
                .email(insertDto.getEmail())
                .nickname(insertDto.getNickname())
                .phone(insertDto.getPhone())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .fromSocial(insertDto.isFromSocial())
                .checkPhone(true)
                .role(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        return "회원가입 완료";
    }

    // 이메일 중복 확인
    private void validateDuplicationMemberEmail(String email) throws IllegalStateException {
        log.info("이메일 중복확인 {}", email);
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new IllegalStateException("중복된 이메일 입니다.");
        }
    }

    @Override
    public void validateDuplicationMemberPhone(String phone) throws IllegalStateException {
        log.info("휴대폰번호 중복확인 {}", phone);
        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원이거나 소셜 로그인을 확인해주세요.");
        }
    }

    @Override
    public MemberDto findId(String phone) throws IllegalStateException {
        log.info("아이디 찾기 service {}", phone);
        Optional<Member> member = memberRepository.findByPhone(phone);
        Optional<Member> member2 = memberRepository.findByPhoneAndFromSocial(phone, true);

        if (member2.isPresent()) {
            throw new IllegalStateException("소셜로그인 으로 가입된 회원입니다.");
        }
        if (!member.isPresent()) {
            throw new IllegalStateException("존재하지 않는 회원 입니다.");
        } else {
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail(member.get().getEmail());
            log.info("아이디찾기 {}", memberDto);
            return memberDto;
        }
    }

    @Override
    public void findIdEmail(String email) throws IllegalStateException {
        log.info("아이디 찾기 service {}", email);
        Optional<Member> member = memberRepository.findByEmail(email);
        Optional<Member> member2 = memberRepository.findByEmailAndFromSocial(email, true);

        if (member2.isPresent()) {
            throw new IllegalStateException("소셜로그인으로 등록된 회원입니다. 소셜로그인을 확인해 주세요.");
        }

        if (!member.isPresent()) {
            throw new IllegalStateException("존재하지 않는 회원 아이디입니다.");
        }
    }

    @Override
    public void equalPhoneEmail(String phone, String email) throws IllegalStateException {
        log.info("휴대폰번호랑 이메일 일치검사 {} {}", phone, email);
        Optional<Member> member = memberRepository.findByEmail(email);
        if (!member.get().getPhone().equals(phone)) {
            throw new IllegalStateException("휴대폰 번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void findPasswordUpdate(FindPasswordDto fDto) {
        log.info("비밀번호 수정 service {}", fDto);

        Member member = memberRepository.findByEmail(fDto.getEmail()).get();

        member.setPassword(passwordEncoder.encode(fDto.getNewPassword()));
        memberRepository.save(member);

    }
}
