package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.AnimalReplyDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalReply;
import com.example.project1.entity.Review;
import com.example.project1.repository.AnimalReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AnimalReplyServiceImpl implements AnimalReplyService {

    private final AnimalReplyRepository animalReplyRepository;

    @Override
    public List<AnimalReplyDto> getReplies(Long sId) {
        Animal animal = Animal.builder().sId(sId).build();
        List<AnimalReply> replies = animalReplyRepository.findByAnimal(animal);

        return replies.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());

    }

    @Override
    public Long create(AnimalReplyDto dto) {
        return animalReplyRepository.save(dtoToEntity(dto)).getRno();
    }

    @Override
    public void remove(Long rno) {
        animalReplyRepository.deleteById(rno);
    }

    @Override
    public AnimalReplyDto getReply(Long rno) {
        return entityToDto(animalReplyRepository.findById(rno).get());
    }

    @Override
    public Long update(AnimalReplyDto dto) {
        AnimalReply reply = animalReplyRepository.findById(dto.getRno()).get();
        reply.setText(dto.getText());

        reply = animalReplyRepository.save(reply);

        return reply.getRno();
    }

}
