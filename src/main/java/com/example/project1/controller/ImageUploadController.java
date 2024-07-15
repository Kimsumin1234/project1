package com.example.project1.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.project1.dto.UploadResultDto;

import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/imageupload")
public class ImageUploadController {

    // application.properties 설정한 변수 가져오기
    @Value("$ {com.example.upload.path}")
    private String uploadPath; // c:\\upload

    @GetMapping("/ex1")
    public void getUpload() {
        log.info("upload Foam 요청 ");
    }

    // fetch ==> @RestController
    // ResponseEntity : 데이터 + 상태코드 / controller에서는 html을 찾으러 가지만 이걸 씀으로써 데이터를 넘김다.
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>> postMethodName(MultipartFile[] uploadFiles) {

        List<UploadResultDto> uploadResultDtos = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFiles) {
            if (!multipartFile.getContentType().startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1);
            log.info("파일정보 - 전체경로 : {} ", oriName); // 파일정보 - 전체경로 : boy1.jpg / 전체경로 : boy2.jpg
            log.info("파일 정보 - 파일명 : {} ", fileName); // 파일 정보 - 파일명 : boy1.jpg / 파일 정보 - 파일명 : boy2.jpg
            // 폴더 생성
            String saveFolderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;
            // java.nio.Path
            Path savePath = Paths.get(saveName);
            try {
                // 원본 파일 저장
                multipartFile.transferTo(savePath);
                // 썸네일 파일 저장
                String thumbSaveName = uploadPath + File.separator + saveFolderPath + File.separator + "s_" + uuid + "_"
                        + fileName;
                File thumbFile = new File(thumbSaveName);
                // 썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // 저장된 파일 정보 객체 생성 후 리스트에 추가
            uploadResultDtos.add(new UploadResultDto(saveFolderPath, uuid, fileName));
        }
        // 일반 controller 라서 템플릿을 찾고있지만 /uploadAjax라는 html은 없어서 에러나고있긴함
        return new ResponseEntity<>(uploadResultDtos, HttpStatus.OK);
    }

    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderStr;
    }

    // 이미지 전송
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("disply 요청" + fileName);
        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> postRemove(String filePath) {
        log.info("파일 삭제 요청 {}", filePath);
        // filePath :
        // http://localhost:8080/upload/2024%5C04%5C29%2F221bf902-27ff-4ab1-860b-d0f4ca1cbb33_boy1.jpg
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(filePath, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            file.delete(); // 원본파일 제거

            File thumbFile = new File(file.getParent(), "s_" + file.getName());
            Boolean result = thumbFile.delete(); // 섬네일 파일 제거

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
