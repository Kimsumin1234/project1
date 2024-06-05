package com.example.project1.service;

import com.example.project1.dto.AnimalHeartDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalHeart;
import com.example.project1.entity.Member;

public interface AnimalHeartService {

    AnimalHeartDto getHeart(Long mid, Long sId);

    void addHeart(AnimalHeartDto animalHeartDto);

    void deleteHeart(AnimalHeartDto animalHeartDto);

    Long animalHeart(Long sId);

    Long countheart(Long sId);

    public default AnimalHeartDto entityToDto(AnimalHeart heart) {
        return AnimalHeartDto.builder()
                .hid(heart.getHid())
                .mid(heart.getMember().getMid())
                .sid(heart.getAnimal().getSId())
                .build();
    }

    public default AnimalHeart dtoToEntity(AnimalHeartDto heartDto) {
        Member member = Member.builder().mid(heartDto.getMid()).build();
        Animal animal = Animal.builder().sId(heartDto.getSid()).build();

        AnimalHeart heart = new AnimalHeart();
        heart.setHid(heartDto.getHid());
        heart.setMember(member);
        heart.setAnimal(animal);

        return heart;
    }
}
