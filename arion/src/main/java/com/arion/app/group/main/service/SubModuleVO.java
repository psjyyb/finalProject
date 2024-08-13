package com.arion.app.group.main.service;

import lombok.Data;

@Data
public class SubModuleVO {
		private String companyCode;
		private String employeeId;
		private Integer moduleNo;
	    private String subModuleName;
	
	    private String urlPattern;
	}