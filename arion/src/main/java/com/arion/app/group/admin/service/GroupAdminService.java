package com.arion.app.group.admin.service;

import java.util.List;
import java.util.Map;

import com.arion.app.security.service.CompanyVO;

public interface GroupAdminService {
	
	List<EmployeeVO> empListSelect(String companyCode);  // 사원목록조회
	
	// 사원등록 화면 부서, 직급 
	List<DepartmentVO> deptListSelect(String companyCode);
	List<RankVO> rankListSelect(String companyCode);
	GroupAdminVO userCntSelect(String companyCode);
	
	int empInsert(EmployeeVO empVO); // 사원등록
	
	int empUpdate(EmployeeVO empVO); // 사원수정
	
	EmployeeVO empInfoSelect(EmployeeVO empVO); // 사원상세보기
	
	int empDelete(EmployeeVO empVO); // 사원삭제
	
	List<GroupAdminVO> endSubSelect(String companyCode); // 지난계약목록조회
	
	GroupAdminVO sunInfoSelect(String companyCode); // 현재 구독중인 계약정보
	
	int saveDept(List<String> list,String companyCode); // 부서저장
	
	int saveRank(RankVO rankVO, String companyCode); // 직급저장
	
	CompanyVO comSelect(String companyCode); // 회사정보 조회
	
	int saveCompany(CompanyVO companyVO); // 회사 정보수정

	Map<String, Object> companyPw(String companyCode,String password); // 비밀번호
}
