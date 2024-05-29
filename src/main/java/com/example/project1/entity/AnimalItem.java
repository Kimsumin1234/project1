package com.example.project1.entity;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

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

@Builder
@ToString(exclude = { "animalCart", "animal" })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnimalItem {

    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    @Id
    private Long itemId;

    private int count; // 카트에 담긴 상품 개수

    @ManyToOne(fetch = FetchType.LAZY)
    private AnimalCart animalCart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;

    private AnimalItem(int count, AnimalCart animalCart, Animal animal) {
        this.count = count;
        this.animalCart = animalCart;
        this.animal = animal;
    }

    public static AnimalItem createCartItem(int count, AnimalCart animalCart, Animal animal) {
        return new AnimalItem(count, animalCart, animal);
    }

}
