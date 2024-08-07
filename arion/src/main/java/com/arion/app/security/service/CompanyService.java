package com.arion.app.security.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

public interface CompanyService {
	
	String insertCompany(CompanyVO cvo);
	boolean IdCheck(String companyId);
	String selectCompanyName(@Param("companyCode") String companyCode);
}
