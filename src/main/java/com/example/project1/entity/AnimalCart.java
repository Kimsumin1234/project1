package com.example.project1.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString(exclude = "member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnimalCart extends BaseEntity {

    @SequenceGenerator(name = "cart_seq_gen", sequenceName = "cart_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq_gen")
    @Id
    private Long cartId;

    private int count; // // 카트에 담긴 상품 개수

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "animalCart")
    private List<AnimalItem> animalItems = new ArrayList<>();

    private AnimalCart(Member member) {
        this.member = member;
    }

    public static AnimalCart createCart(Member member) {
        return new AnimalCart(member);
    }

}
