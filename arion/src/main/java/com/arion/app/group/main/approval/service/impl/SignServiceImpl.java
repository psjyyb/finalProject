package com.arion.app.group.main.approval.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.main.approval.mapper.SignMapper;
import com.arion.app.group.main.approval.service.SignService;

@Service
public class SignServiceImpl implements SignService {
	
	@Autowired
	SignMapper mapper;
	
	@Override
	public String empSign(String companyCode, String employeeId) {
		// TODO Auto-generated method stub
		return mapper.empSign(companyCode, employeeId);
	}

	@Override
	public Map<String, Object> insertSign(EmployeeVO employeeVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = mapper.insertSign(employeeVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", employeeVO);
		
		return map;
	}

}
