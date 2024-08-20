package com.arion.app.group.main.approval.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.admin.service.RankVO;

import lombok.Data;

@Data
public class ApprovalVO {
	private int apprNo;
	private String apprStatus;
	private String apprReason;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date signDate;
	private int sequence;
	private String companyCode;
	private int docNo;
	private int employeeNo;
	private int apprLine;
	
	private String employeeName;
	private String rankName;
	private String departmentName;
	private int rankRanking;
	private String signImg;
	private RankVO rank;
}
