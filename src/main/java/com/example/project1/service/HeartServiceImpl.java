package com.example.project1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project1.dto.HeartDto;
import com.example.project1.entity.Heart;
import com.example.project1.entity.Member;
import com.example.project1.entity.Review;
import com.example.project1.repository.HeartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
    private final HeartRepository heartRepository;

    @Transactional
    @Override
    public void addHeart(HeartDto heartDto) {

        Heart heart = dtoToEntity(heartDto);
        log.info(heart.getReview());
        log.info(heart.getMember());

        heartRepository.save(heart);

    }

    @Transactional
    @Override
    public void deleteHeart(HeartDto heartDto) {
        Heart heart = dtoToEntity(heartDto);

        Member member = Member.builder().mid(heart.getMember().getMid()).build();
        Review review = Review.builder().rno(heart.getReview().getRno()).build();

        heart = heartRepository.findByMemberAndReview(member, review);

        heartRepository.delete(heart);
    }

    @Override
    public HeartDto getHeart(Long mid, Long rno) {
        Member member = Member.builder().mid(mid).build();
        Review review = Review.builder().rno(rno).build();

        Optional<Heart> heartOptional = Optional.ofNullable(heartRepository.findByMemberAndReview(member, review));

        if (heartOptional.isPresent()) {
            Heart heart = heartOptional.get();
            return entityToDto(heart);
        } else {
            return null;
        }

    }

    @Override
    public Long reviewHeart(Long rno) {
        Review review = Review.builder().rno(rno).build();

        List<Heart> heart = heartRepository.findByReview(review);
        if (heart == null) {
            return 0L;
        }
        return (long) heart.size();
    }

}
