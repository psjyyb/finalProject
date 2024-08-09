package com.arion.app.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.security.mapper.HomeModuleMapper;
import com.arion.app.security.service.HomeModuleService;
import com.arion.app.security.service.HomeModuleVO;

@Service
public class HomeModuleServiceImpl implements HomeModuleService {
	
	@Autowired
	HomeModuleMapper mapper;
	
	@Override
	public List<HomeModuleVO> selectModule() {
		// TODO Auto-generated method stub
		return mapper.selectModule();
	}

	@Override
	public List<HomeModuleVO> explanModule(int moduleNo) {

		return mapper.explanModule(moduleNo);
	}


}
