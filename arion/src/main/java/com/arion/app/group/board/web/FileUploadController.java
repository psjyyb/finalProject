package com.arion.app.group.board.web;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	 @PostMapping("/uploadImage")
	    public String uploadImage(@RequestParam("file") MultipartFile file) {
	        // 업로드된 파일을 D:/upload 경로에 저장
	        String uploadDir = "D:/upload/";
	        String fileName = file.getOriginalFilename();
	        File destinationFile = new File(uploadDir + fileName);

	        try {
	            file.transferTo(destinationFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "fail";
	        }

	        return "/uploaded/" + fileName;  // 파일이 저장된 후, 파일에 접근할 수 있는 URL 반환
	    }
}
