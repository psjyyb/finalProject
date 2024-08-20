package com.arion.app.group.main.approval.service;

import java.sql.Clob;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.admin.service.RankVO;

import lombok.Data;

@Data
public class DocumentVO {
	private int docNo;
	private String docTitle;
	private String docName;
	private String docContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date finishDate;
	private String docStatus;
	private String companyCode;
	private int employeeNo;
	private String apprStatus;
	
	private String employeeName;
	private String rankName;
	private String departmentName;
	
	private RankVO rank;
}
