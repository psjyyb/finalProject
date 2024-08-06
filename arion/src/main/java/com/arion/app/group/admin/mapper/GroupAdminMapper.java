package com.arion.app.group.admin.mapper;

import java.util.List;

import com.arion.app.group.admin.service.EmployeeVO;

public interface GroupAdminMapper {
	
	List<EmployeeVO> selectempList(String companyCode); // 사원목록조회
	
}
