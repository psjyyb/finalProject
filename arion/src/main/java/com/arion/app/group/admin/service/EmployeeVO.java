package com.arion.app.group.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmployeeVO {
	private int employeeNo;
	private String employeeName;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date hireDate;
	private String employeeId;
	private String employeePw;
	private String departmentName;
	private String rankName;
	private String employeeResp;
	private String companyCode;
	
}
