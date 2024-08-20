package com.arion.app.group.main.approval.service;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.admin.service.RankVO;

import lombok.Data;

@Data
public class DocAccessVO {
	private int accessNo;
	private int employeeNo;
	private int accessView;
	private int accessEdit;
	private String companyCode;
	private int reference;
	private int accessAppr;
	private int docNo;
	
	private String departmentName;
	private String employeeName;
	private String employeeResp;
	private String rankName;
	private int rankRanking;
	
	private EmployeesVO employee;
	private RankVO rank;
}
