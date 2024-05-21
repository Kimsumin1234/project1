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
                .sid(reply.getAnimal().getSId())
                .email(reply.getMember().getEmail())
                .mid(reply.getMember().getMid())
                .nickname(reply.getMember().getNickname())
                .text(reply.getText())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();
    }

    // dto → entity
    public default AnimalReply dtoToEntity(AnimalReplyDto dto) {
        AnimalReply reply = new AnimalReply();
        reply.setRno(dto.getRno());
        reply.setText(dto.getText());
        reply.setMember(Member.builder().mid(dto.getMid()).build());
        reply.setCreatedDate(dto.getCreatedDate());
        reply.setAnimal(Animal.builder().sId(dto.getSid()).build());
        return reply;
    }

}