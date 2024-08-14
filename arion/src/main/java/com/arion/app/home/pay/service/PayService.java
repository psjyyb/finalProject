package com.arion.app.home.pay.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.security.service.CompanyVO;

public interface PayService {
	
	CompanyVO selectCom(String companyCode,PayVO payVO); // 회사정보
	
	int findLastNo(); // 계약서 번호가져오기
	
	public String requestBillingKey(String customerKey, String authKey); // 빌링키 발급
	 
	// 결제시 이루어지는 services
	Map<String, Object> payEnd(ContractVO contractVO); // 결제합체
	
	int contractInsert(ContractVO contractVO); // 계약서 등록
	
	int useModuleInsert(String moduleName,String companyCode,int contractNo); // 사용모듈 등록
	
	int payInsert(ContractVO contractVO); // 결제 등록
	
	int payDetailInsert(int payNo, String moduleName); // 결제 상세등록
	
	int comRespUpdate(ContractVO contractVO); // 회사테이블 권한수정
	
	CompanyVO emailNameSelect(String companyCode); // 메일,이름 가져오기
	
	
	// 정기결제 메인 서비스
	public Map<String, Object> schedulePayEnd(); 
	
	
	void stateUpdate(); // 계약기간이 끝난계약 만료로 변경.
}
