package com.example.project1.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = { "replyer", "review", "replyComment" })
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReply extends BaseEntity {
    @SequenceGenerator(name = "animal_reviewReply_seq_gen", sequenceName = "animal_reviewReply_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_reviewReply_seq_gen")
    @Id
    private Long replyNo;

    @Column
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply")
    private List<ReviewReplyComment> replyComment = new ArrayList<>();

}
