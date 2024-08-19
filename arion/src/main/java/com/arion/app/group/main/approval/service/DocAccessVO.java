package com.arion.app.group.main.approval.service;

import lombok.Data;

@Data
public class DocAccessVO {
	private int accessNo;
	private int employeeNo;
	private String accessView;
	private String accessEdit;
	private String companyCode;
	private String reference;
	private String accessAppr;
	private int docNo;
	
}
