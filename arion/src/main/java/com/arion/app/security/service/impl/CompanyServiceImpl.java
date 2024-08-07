package com.arion.app.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arion.app.security.mapper.UserMapper;
import com.arion.app.security.service.CompanyService;
import com.arion.app.security.service.CompanyVO;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserMapper mapper;
	
	@Override
	public String insertCompany(CompanyVO cvo) {
		 String encodedPassword = passwordEncoder.encode(cvo.getCompanyPw());
	     cvo.setCompanyPw(encodedPassword);
	     
	     mapper.insertCompany(cvo);
	     return cvo.getCompanyCode();
	}

	@Override
	public boolean IdCheck(String companyId) {
		return mapper.checkId(companyId) > 0;
	}

	@Override
	public String selectCompanyName(String companyCode) {
		return mapper.selectCompanyName(companyCode);
	}

}
