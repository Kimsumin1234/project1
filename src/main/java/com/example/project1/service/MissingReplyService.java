package com.example.project1.service;

import java.util.List;
import com.example.project1.dto.MissingReplyDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Missing;
import com.example.project1.entity.MissingReply;

public interface MissingReplyService {

    List<MissingReplyDto> getList(Long missno);

    MissingReplyDto getReply(Long missrno);

    Long createReply(MissingReplyDto replyDto);

    void removeReply(Long missrno);

    Long updateReply(MissingReplyDto replyDto);

    public default MissingReplyDto entityToDto(MissingReply missingReplyReply) {

        return MissingReplyDto.builder()
                .missrno(missingReplyReply.getMissrno())
                .text(missingReplyReply.getText())
                .missno(missingReplyReply.getMissing().getMissno())
                .mid(missingReplyReply.getMember().getMid())
                .nickname(missingReplyReply.getMember().getNickname())
                .email(missingReplyReply.getMember().getEmail())
                .createdDate(missingReplyReply.getCreatedDate())
                .lastModifiedDate(missingReplyReply.getLastModifiedDate())
                .build();

    }

    public default MissingReply dtoToEntity(MissingReplyDto missingReplyDto) {

        MissingReply missingReply = new MissingReply();
        missingReply.setMissrno(missingReplyDto.getMissrno());
        missingReply.setText(missingReplyDto.getText());
        missingReply.setMissing(Missing.builder().missno(missingReplyDto.getMissno()).build());
        missingReply.setMember(Member.builder().mid(missingReplyDto.getMid()).build());
        missingReply.setCreatedDate(missingReplyDto.getCreatedDate());
        return missingReply;
    }
}
