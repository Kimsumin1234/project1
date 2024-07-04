package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.MissingReplyDto;
import com.example.project1.entity.MissingReply;
import com.example.project1.repository.MissingReplyRepository;
import com.example.project1.repository.MissingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MissingReplyServiceImpl implements MissingReplyService {

    private final MissingReplyRepository replyRepository;
    private final MissingRepository missingRepository;

    @Transactional
    @Override
    public List<MissingReplyDto> getList(Long missno) {
        List<MissingReply> missingReplys = missingRepository.getMissingReplies(missno);

        return missingReplys.stream().map(missingReply -> entityToDto(missingReply)).collect(Collectors.toList());
    }

    @Override
    public Long createReply(MissingReplyDto replyDto) {

        log.info("댓글 등록 {}", replyDto.getMissno());

        return replyRepository.save(dtoToEntity(replyDto)).getMissrno();
    }

    @Transactional
    @Override
    public void removeReply(Long missrno) {
        replyRepository.deleteById(missrno);
    }

    @Override
    public MissingReplyDto getReply(Long missrno) {
        MissingReply missingReply = replyRepository.findById(missrno).get();
        return entityToDto(missingReply);
    }

    @Override
    public Long updateReply(MissingReplyDto replyDto) {
        return replyRepository.save(dtoToEntity(replyDto)).getMissrno();
    }

}
