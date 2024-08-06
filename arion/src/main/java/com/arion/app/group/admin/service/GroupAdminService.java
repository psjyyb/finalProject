package com.arion.app.group.admin.service;

import java.util.List;

public interface GroupAdminService {
	
	List<EmployeeVO> empListSelect(String companyCode);  // 사원목록조회
	
}
