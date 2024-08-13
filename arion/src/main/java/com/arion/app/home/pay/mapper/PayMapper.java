package com.arion.app.home.pay.mapper;

import com.arion.app.home.pay.service.ContractVO;
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
	
	
}
