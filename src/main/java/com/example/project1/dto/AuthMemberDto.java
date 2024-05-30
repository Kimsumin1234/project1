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

    private MemberDto memberDto;
    private boolean fromSocial;

    private Map<String, Object> attr;

    private String name;
    private String provider;
    private String providerId;

    public AuthMemberDto(String username, String password, boolean fromSocial, String provider, String providerId,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.provider = provider;
        this.providerId = providerId;
    }

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
}
