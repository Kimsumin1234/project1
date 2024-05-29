package com.example.project1.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class AuthMemberDto extends User {

    private Long mid;
    // private String email

    private MemberDto memberDto;

    public AuthMemberDto(String username, String password, Long mid,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthMemberDto(MemberDto memberDto) {
        // super(username, password, authorities);
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole())));
        this.memberDto = memberDto;
    }

}
