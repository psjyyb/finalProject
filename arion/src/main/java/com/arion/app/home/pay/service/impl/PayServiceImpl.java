package com.arion.app.home.pay.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	@Override
	public int contractInsert(ContractVO contractVO) {	
		List<String> moduleNames = contractVO.getModuleNames();
		if (moduleNames != null && !moduleNames.isEmpty()) {
            // 첫 번째 요소에서 "[" 제거
            String firstModule = moduleNames.get(0).replace("[", "").trim();
            moduleNames.set(0, firstModule);

            // 마지막 요소에서 "]" 제거
            String lastModule = moduleNames.get(moduleNames.size() - 1).replace("]", "").trim();
            moduleNames.set(moduleNames.size() - 1, lastModule);
        }
		moduleNames.forEach(a ->{
			payMapper.insertSubModule(a,contractVO.getCompanyCode());
		});
		return payMapper.insertContract(contractVO);
	}
}
