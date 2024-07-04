package com.example.project1.service;

import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Animal;

public interface AdoptApiService {

    PageResultDto<AnimalDto, Object[]> getList(PageRequestDto requestDto);

    AnimalDto getRow(Long sId);

    // entity → dto
    public default AnimalDto entityToDto(Animal animal, Long animalCnt) {
        return AnimalDto.builder()
                .sId(animal.getSId())
                .filename(animal.getFilename())
                .happenDt(animal.getHappenDt())
                .kindCd(animal.getKindCd())
                .colorCd(animal.getColorCd())
                .age(animal.getAge())
                .weight(animal.getWeight())
                .noticeNo(animal.getNoticeNo())
                .noticeSdt(animal.getNoticeSdt())
                .noticeEdt(animal.getNoticeEdt())
                .popfile(animal.getPopfile())
                .procssState(animal.getProcssState())
                .sexCd(animal.getSexCd())
                .neuterYn(animal.getNeuterYn())
                .specialMark(animal.getSpecialMark())
                .careNm(animal.getCareNm())
                .careTel(animal.getCareTel())
                .careAddr(animal.getCareAddr())
                .orgNm(animal.getOrgNm())
                .orgNmc(animal.getOrgNmc())
                .officetel(animal.getOfficetel())
                .animalCnt(animalCnt != null ? animalCnt : 0)
                .build();
    }

    // dto → entity
    public default Animal dtoToEntity(AnimalDto dto) {
        return Animal.builder()
                .sId(dto.getSId())
                .filename(dto.getFilename())
                .happenDt(dto.getHappenDt())
                .kindCd(dto.getKindCd())
                .colorCd(dto.getColorCd())
                .age(dto.getAge())
                .weight(dto.getWeight())
                .noticeNo(dto.getNoticeNo())
                .noticeSdt(dto.getNoticeSdt())
                .noticeEdt(dto.getNoticeEdt())
                .popfile(dto.getPopfile())
                .procssState(dto.getProcssState())
                .sexCd(dto.getSexCd())
                .neuterYn(dto.getNeuterYn())
                .specialMark(dto.getSpecialMark())
                .careNm(dto.getCareNm())
                .careTel(dto.getCareTel())
                .careAddr(dto.getCareAddr())
                .orgNm(dto.getOrgNm())
                .orgNmc(dto.getOrgNmc())
                .officetel(dto.getOfficetel())
                .build();
    }
}
