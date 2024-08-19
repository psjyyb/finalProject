package com.arion.app.group.main.approval.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.main.approval.service.DocAccessVO;

public interface DocAccessMapper {
	public int insertAccess(DocAccessVO docAccessVO);
	List<EmployeesVO> selectAddReference(@Param("employeeNo") int employeeNo, @Param("companyCode") String companyCode);
	
}
