package com.arion.app.group.main.service;

import java.util.Date;

import lombok.Data;

@Data
public class AttVO {
	private Integer attNo;
	private Integer empNo;
	private Date startTime;
	private Date endTime;
	private String state;
	private Date attDate;
	private String companyCode;

	
}