package com.example.project1.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.Review;
import com.example.project1.entity.ReviewImage;
import com.example.project1.repository.AnimalHeartRepository;
import com.example.project1.repository.AnimalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdoptApiServiceImpl implements AdoptApiService {

    private final AnimalRepository animalRepository;
    private final AnimalHeartRepository animalHeartRepository;

    @Override
    public PageResultDto<AnimalDto, Object[]> getList(PageRequestDto requestDto) {
        // Pageable pageable = requestDto.getPageable(Sort.by("sId").descending());

        // Page<Animal> result = animalRepository
        // .findAll(animalRepository.makePredicate(requestDto.getType(),
        // requestDto.getKeyword()), pageable);

        // Function<Animal, AnimalDto> fn = (entity -> entityToDto(entity));
        // return new PageResultDto<>(result, fn);

        Page<Object[]> result = animalRepository.list(requestDto.getType(),
                requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("sId").descending()));

        Function<Object[], AnimalDto> fn = (entity -> entityToDto((Animal) entity[0], (Long) entity[1]));
        return new PageResultDto<>(result, fn);

    }

    @Override
    public AnimalDto getRow(Long sId) {
        Animal entity = animalRepository.findById(sId).get();
        return entityToDto((entity), 0L);

    }

}
