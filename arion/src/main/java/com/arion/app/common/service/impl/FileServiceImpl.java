package com.arion.app.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.mapper.FileMapper;
import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileMapper mapper;
    
    @Transactional
    @Override
    public String insertFiles(MultipartFile[] files, String tableName, int keyNo, String companyCode) {
        List<FileVO> fileVOList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();
        String folderPath = makeFolder();
        String saveName = "";

        if (files != null) {
            int i = 1;
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String uniqueFileName = uuid + "_" + originalFilename;
                saveName = Paths.get(uploadPath, folderPath, uniqueFileName).toString();
                
                try {
                    file.transferTo(Paths.get(saveName));
                } catch (IOException e) {
                	e.printStackTrace();
                }

                FileVO fileVO = new FileVO();
                fileVO.setFileName(uniqueFileName);
                fileVO.setFileOriginalName(originalFilename);
                fileVO.setFilePath(saveName);
                fileVO.setFileTurn(i++);
                fileVO.setFileType(file.getContentType());
                fileVO.setCompanyCode(companyCode);
                fileVO.setTableName(tableName);
                fileVO.setKeyNo(keyNo);

                fileVOList.add(fileVO);
                mapper.insertFiles(fileVO);
            }
        }

        return saveName;
    }

    @Override
    public String updateFiles(MultipartFile[] files, String tableName, int keyNo, String companyCode) {
        deleteFiles(tableName, keyNo, companyCode);
        return insertFiles(files, tableName, keyNo, companyCode);
    }

    @Override
    public List<FileVO> selectFiles(String tableName, int keyNo, String companyCode) {
        return mapper.selectFiles(tableName, keyNo, companyCode);
    }

    @Override
    public int deleteFiles(String tableName, int keyNo, String companyCode) {
        return mapper.deleteFiles(tableName, keyNo, companyCode);
    }
	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath);
		// File newFile= new File(dir,"파일명");
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}
}
