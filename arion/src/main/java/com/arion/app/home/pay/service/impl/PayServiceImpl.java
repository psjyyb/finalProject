package com.arion.app.home.pay.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.home.pay.mapper.PayMapper;
import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.security.service.CompanyVO;

@Service
public class PayServiceImpl implements PayService{
	
	private GroupAdminMapper gaMapper;
	private PayMapper payMapper;
	
	@Autowired
	public PayServiceImpl(GroupAdminMapper groupAdminMapper,PayMapper payMapper) {
		this.gaMapper = groupAdminMapper;
		this.payMapper = payMapper;
	}
	
	@Override
	public CompanyVO selectCom(String companyCode,PayVO payVO) {
		CompanyVO cvo = gaMapper.selectCom(companyCode);
		 LocalDate date = LocalDate.now();
		 DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 String sdate = date.format(fm);
		 int month = Integer.parseInt(payVO.getSubscriptionPeriod())/30;
		 LocalDate fdate = date.plusMonths(month);
		 cvo.setStartDate(sdate);
		 cvo.setFinalDate(fdate);
		return cvo;
	}
	@Override
	public int findLastNo() {
		return payMapper.findLastNo();
	}
	@Override
	public int contractInsert(ContractVO contractVO) {	
		
		return payMapper.insertContract(contractVO);
	}
}
