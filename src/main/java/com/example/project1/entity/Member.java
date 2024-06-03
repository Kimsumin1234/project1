package com.example.project1.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.project1.constant.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@Entity
public class Member extends BaseEntity {

    @SequenceGenerator(name = "animal_member_seq_gen", sequenceName = "animal_member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_member_seq_gen")
    @Id
    private Long mid;

    @Column(unique = true) // 중복불가 유니크 제약조건
    private String email;

    @Column(unique = true) // 중복불가 유니크 제약조건
    private String phone;

    private boolean checkPhone;

    @Column(unique = true) // 중복불가 유니크 제약조건
    private String nickname;

    @Column(nullable = false)
    private String password;

    private boolean fromSocial; // 소셜가입

    private String provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

}
