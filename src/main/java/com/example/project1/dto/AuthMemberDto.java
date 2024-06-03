package com.example.project1.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class AuthMemberDto extends User implements OAuth2User {

    private Long mid;
    // private String email

    private MemberDto memberDto;
    private Map<String, Object> attr;

    private boolean fromSocial;
    private String provider;
    private String providerId;
    // 그냥 User 클래스만 사용하는 상황일 경우
    // String username, String password,
    // Collection<? extends GrantedAuthority> authorities 데이터만 담김
    // User 클래스만 사용한 상태로 /auth 치고 들어가면 기본데이터 정보만 담긴다
    // /auth 에 기본데이터 말고 다른 데이터를 추가로 담고 싶으면
    // User 를 상속받는 클래스를 만들고 private MemberDto memberDto; 같은
    // 변수를 만들어서 추가로 데이터를 담을수있다

    public AuthMemberDto(String username, String password, boolean fromSocial, String provider, String providerId,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.provider = provider;
        this.providerId = providerId;
    }

    // List.of : Role 이 여러개가 있을수있어서 1개여도 그냥 사용함
    public AuthMemberDto(MemberDto memberDto, boolean fromSocial) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole())));
        this.memberDto = memberDto;
        this.fromSocial = fromSocial;
    }

    public AuthMemberDto(MemberDto memberDto, boolean fromSocial, String provider, String providerId) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole())));
        this.memberDto = memberDto;
        this.fromSocial = fromSocial;
        this.provider = provider;
        this.providerId = providerId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.memberDto.getNickname();
    }
}
