package com.example.project1.service;

import com.example.project1.dto.AnimalDto;
import com.example.project1.dto.PageRequestDto;
import com.example.project1.dto.PageResultDto;
import com.example.project1.entity.Animal;

public interface AdoptApiService {

    PageResultDto<AnimalDto, Animal> getList(PageRequestDto requestDto);

    // AdoptApiDto getRow(Long sId);

    // entity → dto
    public default AnimalDto entityToDto(Animal adoptApi) {
        return AnimalDto.builder()
                .sId(adoptApi.getSId())
                .filename(adoptApi.getFilename())
                .happenDt(adoptApi.getHappenDt())
                .kindCd(adoptApi.getKindCd())
                .colorCd(adoptApi.getColorCd())
                .age(adoptApi.getAge())
                .weight(adoptApi.getWeight())
                .noticeNo(adoptApi.getNoticeNo())
                .noticeSdt(adoptApi.getNoticeSdt())
                .noticeEdt(adoptApi.getNoticeEdt())
                .popfile(adoptApi.getPopfile())
                .procssState(adoptApi.getProcssState())
                .sexCd(adoptApi.getSexCd())
                .neuterYn(adoptApi.getNeuterYn())
                .specialMark(adoptApi.getSpecialMark())
                .careNm(adoptApi.getCareNm())
                .careTel(adoptApi.getCareTel())
                .careAddr(adoptApi.getCareAddr())
                .orgNm(adoptApi.getOrgNm())
                .orgNmC(adoptApi.getOrgNmC())
                .officetel(adoptApi.getOfficetel())
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
                .orgNmC(dto.getOrgNmC())
                .officetel(dto.getOfficetel())
                .build();
    }
}
