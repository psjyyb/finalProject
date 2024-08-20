package com.arion.app.common.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public String insertFiles(MultipartFile[] files, String tableName, int keyNo, String companyCode);

	public String updateFiles(MultipartFile[] files, String tableName, int keyNo, String companyCode);

	List<FileVO> selectFiles(String tableName, int keyNo, String companyCode);
	
	public int deleteFiles(@Param("tableName") String tableName, @Param("keyNo") int keyNo, @Param("companyCode") String companyCode);
}
