package com.example.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalReply;
import com.example.project1.entity.Member;

@SpringBootTest
public class AnimalReplyRepositoryTest {

    @Autowired
    private AnimalReplyRepository animalReplyRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long sId = (long) (Math.random() * 100) + 1;
            Animal animal = Animal.builder().sId(sId).build();

            Long mid = (long) (Math.random() * 100) + 38;
            Member member = Member.builder().mid(mid).build();

            AnimalReply reply = AnimalReply.builder()
                    .member(member)
                    .animal(animal)
                    .text("Reply..." + i)
                    .build();
            animalReplyRepository.save(reply);
        });

    }
}