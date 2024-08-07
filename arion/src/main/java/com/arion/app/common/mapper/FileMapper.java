package com.arion.app.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.FileVO;

@Mapper
public interface FileMapper {
	
	 void insertFiles(FileVO file);
	 
	 List<FileVO> selectFiles(@Param("tableName") String tableName, @Param("keyNo") int keyNo, @Param("companyCode") String companyCode);
	 
	 int deleteFiles(@Param("tableName") String tableName, @Param("keyNo") int keyNo, @Param("companyCode") String companyCode);
}
