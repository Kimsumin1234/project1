package com.example.project1.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project1.dto.MissingDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;

import com.example.project1.repository.MissingImageRepository;
import com.example.project1.repository.MissingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MissingServiceImpl implements MissingService {

    private final MissingRepository missingRepository;
    private final MissingImageRepository missingImageRepository;

    @Transactional
    @Override
    public PageResultDto<MissingDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = missingRepository.list(requestDto.getType(),
                requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("missno").descending()));

        Function<Object[], MissingDto> fn = (entity -> entityToDto((Missing) entity[0],
                (List<Missingimage>) Arrays.asList((Missingimage) entity[1]), (Long) entity[2],
                (String) entity[3], (String) entity[4], (Long) entity[5]));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public MissingDto getRow(Long missno) {
        List<Object[]> result = missingImageRepository.getRow(missno);

        Missing missing = (Missing) result.get(0)[0];
        Long mid = (Long) result.get(0)[2];
        String email = (String) result.get(0)[3];
        String nickname = (String) result.get(0)[4];
        Long reviewCnt = (Long) result.get(0)[5];

        List<Missingimage> missingImages = new ArrayList<>();
        result.forEach(arr -> {
            Missingimage missingImage = (Missingimage) arr[1];
            missingImages.add(missingImage);
        });

        return entityToDto(missing, missingImages, mid, email, nickname, reviewCnt);
    }

    @Override
    public Long missingInsert(MissingDto missingDto) {

        Map<String, Object> entityMap = dtoToEntity(missingDto);
        Missing missing = missingRepository.save((Missing) entityMap.get("missing"));
        List<Missingimage> missingImages = (List<Missingimage>) entityMap.get("imgList");

        missingImages.forEach(img -> missingImageRepository.save(img));

        return missing.getMissno();
    }

    @Transactional
    @Override
    public Long missingUpdate(MissingDto missingDto) {

        Map<String, Object> entityMap = dtoToEntity(missingDto);

        Missing entity = missingRepository.findById(missingDto.getMissno()).get();
        entity.setTitle(missingDto.getTitle());
        entity.setText(missingDto.getText());

        missingRepository.save(entity);

        // missing 기존 image 제거
        Missing missing = (Missing) entityMap.get("missing");
        missingImageRepository.deleteByMissing(missing);

        // Missingimage 삽입
        List<Missingimage> missingImages = (List<Missingimage>) entityMap.get("imgList");
        if (missingImages != null) {
            for (Missingimage missingimage : missingImages) {
                missingimage.setMissing(missing);
                missingImageRepository.save(missingimage);
            }
        }
        missingImages.forEach(img -> missingImageRepository.save(img));

        // missingRepository.save((Missing) entityMap.get("missing"));

        return missing.getMissno();
    }

    @Transactional
    @Override
    public void missingRemove(Long missno) {

        // cascade 해서 글만 삭제해도 자동으로 자식도 지워지게함.
        missingRepository.deleteById(missno);

        // 모든 댓글 지우고, 글 삭제
        // missingImageRepository.deleteByMissing(missing);
        // Missing missing = missingRepository.findById(missno).get();
    }

}
