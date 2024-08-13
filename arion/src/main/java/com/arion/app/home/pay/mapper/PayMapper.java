package com.arion.app.home.pay.mapper;

import java.util.List;

import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.UseModuleVO;
import com.arion.app.security.service.CompanyVO;

public interface PayMapper {
	
	int findLastNo(); // 계약서 번호가져오기
	
	CompanyVO selectComInfo(String compayCode); // 회사정보 결제시 동적으로 넣기위해
	
	// 결제
	int insertPay(ContractVO contractVO); // 결제테이블 insert
	
	int insertPayDetail(int payNo, String moduleName); // 결제 상세 테이블 insert
	
	int insertContract(ContractVO contractVO);  //계약서작성
	
	int insertSubModule(String moduleName,String companyCode,int contractNo); // 구독중인 모듈 등록
	
	int updateComResp(ContractVO contractVO); // 회사테이블 권한수정
	
	// 정기결제 
	
	List<ContractVO> contractList(); // 계약중 그리고 계약종료일이 현재날짜보다 큰 계약에 대한 모든 계약조회
	List<UseModuleVO> useModule(int contractNo); // 계약건에 대한 사용모듈 조회 pay detail 에 넣을 정보
	
	
}
