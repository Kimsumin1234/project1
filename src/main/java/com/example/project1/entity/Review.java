package com.example.project1.entity;

import jakarta.persistence.Column;
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

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class Review extends BaseEntity {

    @SequenceGenerator(name = "animal_review_seq_gen", sequenceName = "animal_review_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_review_seq_gen")
    @Id
    private Long rno;

    @Column
    private String text;

    @Column
    private String title;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

}
