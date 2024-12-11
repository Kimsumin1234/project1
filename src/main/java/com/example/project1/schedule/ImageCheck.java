package com.example.project1.schedule;

import com.example.project1.entity.Missingimage;
import com.example.project1.entity.ReviewImage;
import com.example.project1.repository.MissingImageRepository;
import com.example.project1.repository.ReviewImageRepository;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ImageCheck {

  private final ReviewImageRepository reviewImageRepository;

  private final MissingImageRepository missingImageRepository;

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 실행
  public void checkFiles() {
    log.info("스케줄링 작업 시작...");

    // 어제 날짜 디렉토리 계산
    LocalDate yesterday = LocalDate.now().minusDays(1);
    String yesterdayFolder = yesterday.format(
      DateTimeFormatter.ofPattern("yyyy/MM/dd")
    );
    File targetDir = new File(uploadPath, yesterdayFolder);

    if (!targetDir.exists() || !targetDir.isDirectory()) {
      log.warn("디렉토리가 존재하지 않음: {}", targetDir.getAbsolutePath());
      return;
    }

    // 디렉토리 내 파일 목록 가져오기
    File[] filesInDir = targetDir.listFiles();
    if (filesInDir == null || filesInDir.length == 0) {
      log.info("디렉토리에 파일이 없습니다: {}", targetDir.getAbsolutePath());
      return;
    }

    // 데이터베이스에서 어제 날짜 파일 목록 가져오기
    LocalDateTime start = yesterday.atStartOfDay();
    LocalDateTime end = yesterday.plusDays(1).atStartOfDay();

    List<ReviewImage> dbReviewImages = reviewImageRepository.findImagesByCreatedDateBetween(
      start,
      end
    );
    List<Missingimage> dbMissingImages = missingImageRepository.findImagesByCreatedDateBetween(
      start,
      end
    );

    // 데이터베이스에 등록된 파일과 썸네일의 전체 경로 목록 생성
    Set<String> dbFilePaths = new HashSet<>();
    dbReviewImages.forEach(image -> {
      dbFilePaths.add(buildFilePath(image, false)); // 원본 파일 경로
      dbFilePaths.add(buildFilePath(image, true)); // 썸네일 파일 경로
    });
    dbMissingImages.forEach(image -> {
      dbFilePaths.add(buildFilePath(image, false)); // 원본 파일 경로
      dbFilePaths.add(buildFilePath(image, true)); // 썸네일 파일 경로
    });

    // 디렉토리 파일과 데이터베이스 파일 비교 및 삭제
    for (File file : filesInDir) {
      String filePath = file.getAbsolutePath();
      if (!dbFilePaths.contains(filePath)) {
        if (file.delete()) {
          log.info("삭제된 파일: {}", filePath);
        } else {
          log.warn("파일 삭제 실패: {}", filePath);
        }
      }
    }

    log.info("스케줄링 작업 완료");
  }

  // 데이터베이스 엔티티로 파일 경로 생성 메서드
  private String buildFilePath(ReviewImage image, boolean isThumbnail) {
    String fileName = isThumbnail
      ? "s_" + image.getUuid() + "_" + image.getImagename() // 썸네일 파일 이름
      : image.getUuid() + "_" + image.getImagename(); // 원본 파일 이름
    return Paths.get(uploadPath, image.getPath(), fileName).toString();
  }

  private String buildFilePath(Missingimage image, boolean isThumbnail) {
    String fileName = isThumbnail
      ? "s_" + image.getUuid() + "_" + image.getImagename() // 썸네일 파일 이름
      : image.getUuid() + "_" + image.getImagename(); // 원본 파일 이름
    return Paths.get(uploadPath, image.getPath(), fileName).toString();
  }
  // 전일자 폴더 구하기
  // private String getFolderYesterday() {
  //   // 어제날짜 추출
  //   LocalDate yesterday = LocalDate.now().minusDays(1);

  //   // 추출한 어제날짜 데이터를 String 타입으로 변환
  //   String strDay = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

  //   // 2024-05-07 ==> 2024\05\07 바꿔서 리턴
  //   // windows 기반 파일경로 : \ , linux 기반 파일경로 : / 운영체제 마다 다르기 때문에
  //   // File.separator 를 사용하면 알아서 운영체제를 구별해서 사용해준다
  //   return strDay.replace("-", File.separator);
  // }

  // // cron : 리눅스 크론탭에서 착안함
  // // 주기결정 : 0(초) *(분0-59) *(시간(0-23)) *(일(1-31)) *(월(1-12)) *(요일(0-7))
  // // 0초 로 정하면 매 0초마다 스케쥴러를 실행해줘 (테스트 중이라 매0초 설정 "0 * * * * *")
  // // 실제로는 새벽시간에 설정 할수도있다 "0 0 2 * * *"
  // @Scheduled(cron = "0 0 * * * *")
  // public void checkFiles() {
  //   log.info("file check task 시작....");

  //   // 데이터베이스 상 어제날짜의 이미지 파일 리스트 가져오기
  //   List<ReviewImage> oldImages = reviewImageRepository.getOldReviewImages();
  //   List<Missingimage> oldImages2 = missingImageRepository.getOldMissingImages();

  //   // entity => dto (MovieService 에서 복사떠옴)
  //   List<ReviewImageDto> reviewImageDtos = oldImages
  //     .stream()
  //     .map(reviewImage -> {
  //       return ReviewImageDto
  //         .builder()
  //         .inum(reviewImage.getInum())
  //         .uuid(reviewImage.getUuid())
  //         .imagename(reviewImage.getImagename())
  //         .path(reviewImage.getPath())
  //         .build();
  //     })
  //     .collect(Collectors.toList());

  //   List<MissingimageDto> missingImageDtos = oldImages2
  //     .stream()
  //     .map(misingImage -> {
  //       return MissingimageDto
  //         .builder()
  //         .inum(misingImage.getInum())
  //         .uuid(misingImage.getUuid())
  //         .imagename(misingImage.getImagename())
  //         .path(misingImage.getPath())
  //         .build();
  //     })
  //     .collect(Collectors.toList());

  //   // dto 내용 수집 ==> 이미지 파일 한개 당 c:\\upload\\2024\\05\\07\\uuid_파일명
  //   List<Path> fileListPaths = reviewImageDtos
  //     .stream()
  //     .map(dto ->
  //       Paths.get(
  //         uploadPath,
  //         dto.getImageURL(),
  //         dto.getUuid() + "_" + dto.getImagename()
  //       )
  //     )
  //     .collect(Collectors.toList());
  //   // 썸네일 이미지 파일명
  //   // .forEach(p -> fileListPaths.add(p)) 사용해서 fileListPaths 에 리스트를 추가함
  //   reviewImageDtos
  //     .stream()
  //     .map(dto ->
  //       Paths.get(
  //         uploadPath,
  //         dto.getImageURL(),
  //         "s_" + dto.getUuid() + "_" + dto.getImagename()
  //       )
  //     )
  //     .forEach(p -> fileListPaths.add(p));

  //   // missingImage 도 추가
  //   missingImageDtos
  //     .stream()
  //     .map(dto ->
  //       Paths.get(
  //         uploadPath,
  //         dto.getImageURL(),
  //         dto.getUuid() + "_" + dto.getImagename()
  //       )
  //     )
  //     .forEach(p2 -> fileListPaths.add(p2));

  //   missingImageDtos
  //     .stream()
  //     .map(dto ->
  //       Paths.get(
  //         uploadPath,
  //         dto.getImageURL(),
  //         "s_" + dto.getUuid() + "_" + dto.getImagename()
  //       )
  //     )
  //     .forEach(p3 -> fileListPaths.add(p3));

  //   // 로그 확인 (테스트)
  //   // for (Path path : fileListPaths) {
  //   // log.info(path);
  //   // }

  //   // 어제날짜 폴더 구하기
  //   File targetDir = Paths.get(uploadPath, getFolderYesterday()).toFile();
  //   // targetDir 안의 파일과 DB 파일 목록 비교후 일치하지 않는 파일 목록 생성
  //   File[] removeFiles = targetDir.listFiles(f ->
  //     fileListPaths.contains(f.toPath()) == false
  //   );

  //   // 작성후 어제날짜 폴더에 다른 이미지 파일을 넣어보고 실제로 삭제가 되는지 테스트
  //   if (removeFiles != null) {
  //     for (File file : removeFiles) {
  //       file.delete();
  //     }
  //   }
  // }
}
