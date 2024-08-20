package com.arion.app.common.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmployeesVO {
	private int employeeNo;
	private String employeeName;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date hireDate;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date resignDate;
	private String employeeResp;
	private String employeeId;
	private String employeePw;
	private String signImg;
	private String departmentName;
	private String rankName;
	private String employeePhone;
}
