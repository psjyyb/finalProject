package com.arion.app.home.pay.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ContractVO {
	private int contractNo;
	private String startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finalDate;
	private String usersCnt;
	private int monthPayPrice;
	private int totalPayPrice;
	private String companyCode;
	private Integer regularDate;
	private String billingkey;
	private String customerkey;
	private String contractSign;
	private int firstMonthAmount;
	
	private List<String> moduleNames;
	
	
	
}
