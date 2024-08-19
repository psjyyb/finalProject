package com.arion.app.group.main.approval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.approval.mapper.HolDocMapper;
import com.arion.app.group.main.approval.service.HolDocService;
import com.arion.app.group.main.approval.service.HolDocVO;

@Service
public class HolDocServiceImpl implements HolDocService {
	
	@Autowired
	HolDocMapper mapper;
	
	@Override
	public void insertHolDoc(HolDocVO holDocVO) {
		
		
	}

}
