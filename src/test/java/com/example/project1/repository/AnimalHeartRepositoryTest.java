package com.example.project1.repository;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.project1.repository.animal.AnimalAnimalHeartRepository;

@SpringBootTest
public class AnimalHeartRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void animalHeart() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("sId").descending());

        Page<Object[]> list = animalRepository.list("", "", pageable);
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

        }
    }

}
