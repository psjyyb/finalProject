package com.arion.app.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.security.service.HomeModuleVO;

@Mapper
public interface HomeModuleMapper {
	
	List<HomeModuleVO> selectModule(); 
	
}
