package com.arion.app.group.main.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.main.approval.mapper.DocumentMapper;
import com.arion.app.group.main.approval.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	DocumentMapper mapper;
	
	@Override
	public List<String> selectDepartment(String companyCode) {
		
		return mapper.selectDepartment(companyCode);
	}

	@Override
	public List<EmployeesVO> selectEmployeeList(String companyCode, String departmentName) {
		// TODO Auto-generated method stub
		return mapper.selectEmployeeList(companyCode, departmentName);
	}

}
