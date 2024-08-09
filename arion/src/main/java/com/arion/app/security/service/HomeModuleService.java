package com.arion.app.security.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HomeModuleService {

	List<HomeModuleVO> selectModule(); 
	List<HomeModuleVO> explanModule(@Param("moduleNo") int moduleNo);
	
}
