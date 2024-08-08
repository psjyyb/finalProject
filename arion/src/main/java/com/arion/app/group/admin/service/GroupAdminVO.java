package com.arion.app.group.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GroupAdminVO {
	private int usersCnt;
	private String companyCode;
	private int contractNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finalDate;
	private String contractState;
	private int totalPayPrice;
}
