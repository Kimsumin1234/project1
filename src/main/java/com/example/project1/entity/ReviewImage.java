package com.example.project1.entity;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "review")
@Entity
public class ReviewImage {

    @SequenceGenerator(name = "animal_review_image_seq_gen", sequenceName = "animal_review_image_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_review_image_seq_gen")
    @Id
    private Long inum;

    private String uuid;

    private String imagename;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

}
