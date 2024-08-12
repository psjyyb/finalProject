package com.arion.app.home.pay.service;

import com.arion.app.security.service.CompanyVO;

public interface PayService {
	
	CompanyVO selectCom(String companyCode); // 회사정보
	
}
