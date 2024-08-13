package com.arion.app.group.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PayListVO {
	private int payNo;
	private int usersCnt;
	private String companyCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date payDate;
	private int contractNo;
	private String orderId;
	private int payPrice;
}
