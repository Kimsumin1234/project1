package com.example.project1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project1.dto.AnimalHeartDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalHeart;
import com.example.project1.entity.Member;
import com.example.project1.repository.AnimalHeartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AnimalHeartServiceImpl implements AnimalHeartService {
    private final AnimalHeartRepository animalHeartRepository;

    @Transactional
    @Override
    public void addHeart(AnimalHeartDto animalHeartDto) {

        AnimalHeart heart = dtoToEntity(animalHeartDto);
        log.info("하트 추가 서비스 {}", heart.getAnimal());
        log.info("하트 추가 서비스 {}", heart.getMember());

        animalHeartRepository.save(heart);

    }

    @Transactional
    @Override
    public void deleteHeart(AnimalHeartDto animalHeartDto) {
        AnimalHeart heart = dtoToEntity(animalHeartDto);

        Member member = Member.builder().mid(heart.getMember().getMid()).build();
        Animal animal = Animal.builder().sId(heart.getAnimal().getSId()).build();

        heart = animalHeartRepository.findByMemberAndAnimal(member, animal);

        animalHeartRepository.delete(heart);
    }

    @Override
    public AnimalHeartDto getHeart(Long mid, Long sId) {
        Member member = Member.builder().mid(mid).build();
        Animal animal = Animal.builder().sId(sId).build();

        Optional<AnimalHeart> heartOptional = Optional
                .ofNullable(animalHeartRepository.findByMemberAndAnimal(member, animal));

        if (heartOptional.isPresent()) {
            AnimalHeart heart = heartOptional.get();
            return entityToDto(heart);
        } else {
            return null;
        }

    }

    @Override
    public Long animalHeart(Long sId) {
        Animal animal = Animal.builder().sId(sId).build();

        List<AnimalHeart> heart = animalHeartRepository.findByAnimal(animal);
        if (heart == null) {
            return 0L;
        }
        return (long) heart.size();
    }

}
