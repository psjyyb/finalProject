package com.arion.app.group.admin.mapper;

import java.time.LocalDate;
import java.util.List;

import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminVO;
import com.arion.app.group.admin.service.PayDetailVO;
import com.arion.app.group.admin.service.PayListVO;
import com.arion.app.group.admin.service.RankVO;
import com.arion.app.security.service.CompanyVO;

public interface GroupAdminMapper {
	
	List<EmployeeVO> selectempList(String companyCode); // 사원목록조회
	
	// 사원등록 화면 부서,직급,현재 사용인원수 넘겨주기
	List<DepartmentVO> selectDeptList(String companyCode);
	List<RankVO> selectRankList(String companyCode);
	GroupAdminVO selectUserCnt(String companyCode); 
	
	
	int insertEmp(EmployeeVO empVO); // 사원등록
	
	EmployeeVO selectEmpInfo(EmployeeVO empVO); // 사원 상세보기
	
	int updateEmp(EmployeeVO empVO); // 사원수정
	
	int deleteEmp(EmployeeVO empVO); // 사원삭제
	
	List<GroupAdminVO> selectEndSub(String companyCode);// 지난 계약조회
	
	GroupAdminVO selectSubInfo(String companyCode); // 현재 계약정보조회
	
	int deptInSave(DepartmentVO dvo); // 부서저장
	int deptDeSave(String companyCode); // 부서저장
	
	int rankInSave(RankVO rankVO); // 직급저장
	int rankDeSave(String companyCode); // 직급저장
	
	CompanyVO selectCom(String companyCode); // 회사정보
	
	int companySave(CompanyVO companyVO); // 회사 정보수정
	
	String companyPw(String companyCode); // 비밀번호
	
	int contractNo(String companyCode); // 계약중인 계약서 번호
	
	// 계약해지
	int cancleContract(int contractNo, String companyCode); // 계약서 해지
	int cancleCompany(String companyCode); // 권한 변경
	
	List<PayListVO> payList(String comcode); // 결제목록
	
	PayListVO payInfo(int payNo); // 결제상세보기
	List<PayDetailVO> payDetailInfo(int payNo); // 결제상세상세보기
	
	int updateContract(LocalDate finalDate, int contractNo); // 계약 갱신
	
}
