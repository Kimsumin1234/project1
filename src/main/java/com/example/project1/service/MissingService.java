package com.example.project1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.project1.dto.MissingDto;
import com.example.project1.dto.MissingimageDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Member;
import com.example.project1.entity.Missing;
import com.example.project1.entity.Missingimage;

public interface MissingService {

    PageResultDto<MissingDto, Object[]> getList(PageRequestDto requestDto);

    MissingDto getRow(Long missno);

    Long missingUpdate(MissingDto missingDto);

    Long missingInsert(MissingDto missingDto);

    void missingRemove(Long missno);

    public default MissingDto missingEntityToDto(Missing missing) {
        return MissingDto.builder()
                .missno(missing.getMissno())
                .title(missing.getTitle())
                .text(missing.getText())
                .createdDate(missing.getCreatedDate())
                .lastModifiedDate(missing.getLastModifiedDate())
                .build();
    }

    // entity, dto 형변환
    public default MissingDto entityToDto(Missing missing, List<Missingimage> missingImages, Long mid, String email,
            String nickname) {
        MissingDto missingDto = MissingDto.builder()
                .missno(missing.getMissno())
                .title(missing.getTitle())
                .text(missing.getText())
                .mid(mid)
                .email(email)
                .nickname(nickname)
                .createdDate(missing.getCreatedDate())
                .lastModifiedDate(missing.getLastModifiedDate())
                .build();

        // 영화 상세 조회 → 이미지를 모두 보여주기
        List<MissingimageDto> missingImageDtos = missingImages.stream().map(misingImage -> {
            return MissingimageDto.builder()
                    .inum(misingImage.getInum())
                    .uuid(misingImage.getUuid())
                    .imagename(misingImage.getImagename())
                    .path(misingImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        missingDto.setMissingImageDtos(missingImageDtos);

        return missingDto;
    }

    public default Map<String, Object> dtoToEntity(MissingDto dto) {
        Map<String, Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(dto.getMid()).build();

        Missing missing = Missing.builder()
                .missno(dto.getMissno())
                .title(dto.getTitle())
                .text(dto.getText())
                .member(member)
                .build();
        entityMap.put("missing", missing);

        List<MissingimageDto> missingImageDtos = dto.getMissingImageDtos();
        if (missingImageDtos != null && missingImageDtos.size() > 0) {
            List<Missingimage> missingImages = missingImageDtos.stream().map(mDto -> {
                Missingimage missingImage = Missingimage.builder()
                        .missing(missing)
                        .imagename(mDto.getImagename())
                        .uuid(mDto.getUuid())
                        .path(mDto.getPath())
                        .build();
                return missingImage;
            }).collect(Collectors.toList());

            // 변환이 끝난 entity list 를 map 담기 : put
            entityMap.put("imgList", missingImages);
        }

        return entityMap;
    }

}
