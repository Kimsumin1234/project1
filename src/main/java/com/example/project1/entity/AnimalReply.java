package com.example.project1.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "animal")
@Builder
@Getter
@Setter
@Entity
public class AnimalReply extends BaseEntity {

    @SequenceGenerator(name = "animal_reply_seq_gen", sequenceName = "animal_reply_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_reply_seq_gen")
    @Id
    private Long rno;

    private String text; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 멤버인 사람만 댓글을 사용할 수 있음

    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;

}
