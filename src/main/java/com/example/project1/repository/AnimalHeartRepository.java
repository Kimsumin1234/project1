package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalHeart;
import com.example.project1.entity.Member;
import java.util.List;

public interface AnimalHeartRepository extends JpaRepository<AnimalHeart, Long> {

    AnimalHeart findByMemberAndAnimal(Member member, Animal animal);

    List<AnimalHeart> findByAnimal(Animal animal);

    List<AnimalHeart> findByMember(Member member);

    void deleteByAnimal(Animal animal);
}
