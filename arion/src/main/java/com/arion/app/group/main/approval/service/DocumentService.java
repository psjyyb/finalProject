package com.arion.app.group.main.approval.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.EmployeesVO;

public interface DocumentService {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
}
