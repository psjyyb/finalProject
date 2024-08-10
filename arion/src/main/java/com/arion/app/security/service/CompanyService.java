package com.arion.app.security.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

public interface CompanyService {
	
	String insertCompany(CompanyVO cvo);
	boolean IdCheck(String companyId);
	String selectCompanyName(@Param("companyCode") String companyCode);
	
	CompanyVO findId (@Param("ceoName") String ceoName, @Param("companyBusinessNumber") int companyBusinessNumber);
    int updatePw (@Param("companyCode") String companyCode, @Param("companyId") String companyId);
    String selectEmail (@Param("companyCode") String companyCode, @Param("companyId") String companyId);
}
