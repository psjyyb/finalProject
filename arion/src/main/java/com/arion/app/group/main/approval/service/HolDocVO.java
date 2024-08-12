package com.arion.app.group.main.approval.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class HolDocVO {
	private int holTempNo;
	private String holType;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date endDate;
	private String companyCode;
	private int docNo;
	private int employeeNo;
	
}
