package com.arion.app.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.security.service.HomeModuleVO;

@Mapper
public interface HomeModuleMapper {
	
	List<HomeModuleVO> selectModule();
	List<HomeModuleVO> explanModule(@Param("moduleNo") int moduleNo);
	
}
