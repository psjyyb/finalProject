package com.arion.app.group.main.approval.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HolDocVO {
	private int holTempNo;
	private String holType;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") 
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") 
	private Date endDate;
	private String companyCode;
	private int docNo;
	private int employeeNo;
	private String employeeName;
	private String departmentName;
	private String rankName;
	private String appliDate;
	private String holReason;
	private String holNote;
	
}
