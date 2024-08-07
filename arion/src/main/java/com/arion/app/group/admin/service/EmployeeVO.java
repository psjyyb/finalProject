package com.arion.app.group.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeVO {
	private int employeeNo;
	private String employeeName;
	private Date hireDate;
	private String employeeId;
	private String employeePw;
	private String departmentName;
	private String rankName;
	private String employeeResp;
	private String companyCode;
	
}
