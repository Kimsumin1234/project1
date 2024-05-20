package com.example.project1.service;

import java.util.List;

import com.example.project1.dto.AnimalReplyDto;
import com.example.project1.dto.ReviewDto;
import com.example.project1.entity.Animal;
import com.example.project1.entity.AnimalReply;
import com.example.project1.entity.Member;

public interface AnimalReplyService {

    List<AnimalReplyDto> getReplies(Long sId);

    Long create(AnimalReplyDto dto);

    void remove(Long rno);

    AnimalReplyDto getReply(Long rno);

    Long update(AnimalReplyDto dto);

    public default AnimalReplyDto entityToDto(AnimalReply reply) {
        return AnimalReplyDto.builder()
                .rno(reply.getRno())
                .sId(reply.getAnimal().getSId())
                .email(reply.getMember().getEmail())
                .nickname(reply.getMember().getNickname())
                .text(reply.getText())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();
    }

    // dto → entity
    public default AnimalReply dtoToEntity(AnimalReplyDto dto) {
        // 부모 가져오기
        Animal animal = Animal.builder().sId(dto.getSId()).build();
        Member member = Member.builder().email(dto.getEmail()).build();

        return AnimalReply.builder()
                .rno(dto.getRno())
                .animal(animal)
                .member(member)
                .text(dto.getText())
                .build();
    }

}
