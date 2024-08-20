package com.arion.app.group.main.approval.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arion.app.group.admin.service.EmployeeVO;

public interface SignService {
	public String empSign(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
	public Map<String, Object> insertSign(EmployeeVO employeeVO);
	
	public void apprSign(SignVO signVO);
}
