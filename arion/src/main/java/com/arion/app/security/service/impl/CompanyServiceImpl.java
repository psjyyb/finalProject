package com.arion.app.security.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.EmailService;
import com.arion.app.security.mapper.UserMapper;
import com.arion.app.security.service.CompanyService;
import com.arion.app.security.service.CompanyVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
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

	@Override
	public CompanyVO findId(String ceoName, int companyBusinessNumber) {
		
		CompanyVO companyVO = mapper.findId(ceoName, companyBusinessNumber);
		if(companyVO != null) {
			String emailTo = selectEmail(companyVO.getCompanyCode(), companyVO.getCompanyId());
			log.debug(emailTo);
			String emailSubject = "ARION 회사코드, 아이디 발송입니다.";
			String emailText = "당신의 회사코드는 " + companyVO.getCompanyCode() + " 입니다. \n"
					+ " 당신의 아이디는 " + companyVO.getCompanyId() + " 입니다." ;	
			
			emailService.sendEmail(emailTo, emailSubject, emailText);
		
		}
		return companyVO;
	}

	@Override
	public int updatePw(String companyCode, String companyId) {
		UUID uuid = UUID.randomUUID();
		String newPw = uuid.toString().replace("-", "").substring(0,8);
		String encodedPassword = passwordEncoder.encode(newPw);
		
		int result = mapper.updatePw(companyCode, companyId, encodedPassword);
		
		if(result > 0) {
			String emailTo = selectEmail(companyCode, companyId);
			String emailSubject = "ARION 임시 비밀번호 발송입니다.";
			String emailText = "당신의 임시 비밀번호는 " + newPw + " 입니다.";
			
			emailService.sendEmail(emailTo, emailSubject, emailText);
		}
		return result;
	}

	@Override
	public String selectEmail(String companyCode, String companyId) {
		
		return mapper.selectEmail(companyCode, companyId);
	}

}
