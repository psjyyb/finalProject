package com.arion.app.home.pay.mapper;

import com.arion.app.home.pay.service.ContractVO;

public interface PayMapper {
	
	int findLastNo(); // 계약서 번호가져오기
	
	int insertContract(ContractVO contractVO);
}
