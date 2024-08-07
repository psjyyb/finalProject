package com.arion.app.group.admin.mapper;

import java.util.List;

import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.RankVO;

public interface GroupAdminMapper {
	
	List<EmployeeVO> selectempList(String companyCode); // 사원목록조회
	
	// 사원등록 화면 부서,직급 넘겨주기
	List<DepartmentVO> selectDeptList(String companyCode);
	List<RankVO> selectRankList(String companyCode);
	
	
	int insertEmp(EmployeeVO empVO); // 사원등록
	
	EmployeeVO selectEmpInfo(EmployeeVO empVO); // 사원 상세보기
	
	int updateEmp(EmployeeVO empVO); // 사원수정
	
	int deleteEmp(EmployeeVO empVO); // 사원삭제
	
	
	
}
