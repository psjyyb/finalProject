package com.arion.app.group.main.approval.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.EmployeesVO;

public interface DocumentMapper {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
	
}
