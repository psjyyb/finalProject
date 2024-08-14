package com.arion.app.group.main.approval.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.approval.mapper.TemplateMapper;
import com.arion.app.group.main.approval.service.TemplateService;
import com.arion.app.group.main.approval.service.TemplateVO;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	TemplateMapper mapper;
	
	@Override
	public List<TemplateVO> tempList(String companyCode) {
		
		return mapper.tempList(companyCode);
	}

	@Override
	public Map<String, Object> insertTemp(TemplateVO templateVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = mapper.insertTemp(templateVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", templateVO);
		
		return map;
	}

	@Override
	public TemplateVO tempInfo(TemplateVO templateVO) {
		// TODO Auto-generated method stub
		return mapper.tempInfo(templateVO);
	}

	@Override
	public int tempDelete(String companyCode, String tempNo) {
		// TODO Auto-generated method stub
		return mapper.tempDelete(companyCode, tempNo);
	}

}
