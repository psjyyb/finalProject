package com.arion.app.home.pay.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.security.service.CompanyVO;

public interface PayService {
	
	CompanyVO selectCom(String companyCode,PayVO payVO); // 회사정보
	
	int findLastNo(); // 계약서 번호가져오기
	
	int contractInsert(ContractVO contractVO); // 계약서 등록
	
	 public String requestBillingKey(String customerKey, String authKey); // 빌링키 발급
}
