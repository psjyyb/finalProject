package com.arion.app.home.pay.service.impl;

import org.springframework.stereotype.Service;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.security.service.CompanyVO;

@Service
public class PayServiceImpl implements PayService{
	
	private GroupAdminMapper gaMapper;

	public PayServiceImpl(GroupAdminMapper groupAdminMapper) {
		this.gaMapper = groupAdminMapper;
	}
	
	@Override
	public CompanyVO selectCom(String companyCode) {
		return gaMapper.selectCom(companyCode);
	}
}
