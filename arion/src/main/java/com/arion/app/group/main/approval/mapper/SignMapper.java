package com.arion.app.group.main.approval.mapper;

import org.apache.ibatis.annotations.Param;

import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.main.approval.service.SignVO;

public interface SignMapper {
	public String empSign(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
	public int insertSign(EmployeeVO employeeVO);
	
	public void apprSign(SignVO signVO);
	
	
}
