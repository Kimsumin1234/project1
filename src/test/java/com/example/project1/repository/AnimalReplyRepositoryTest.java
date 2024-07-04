package com.example.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalReply;
import com.example.project1.entity.Member;
import com.example.project1.entity.Missing;
import com.example.project1.entity.MissingReply;

@SpringBootTest
public class AnimalReplyRepositoryTest {

    @Autowired
    private AnimalReplyRepository animalReplyRepository;

    @Autowired
    private MissingReplyRepository missingReplyRepository;

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

    // MissingReply
    @Test
    public void minsertTest() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            // long missno = (long) (Math.random() * 100) + 1;
            Missing missing = Missing.builder().missno(1L).build();

            Long mid = (long) (Math.random() * 100) + 21;
            Member member = Member.builder().mid(21L).build();

            MissingReply reply = MissingReply.builder()
                    .member(member)
                    .missing(missing)
                    .text("Reply..." + i)
                    .build();
            missingReplyRepository.save(reply);
        });

    }
}