package com.arion.app.group.main.approval.service;

import java.sql.Clob;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DocumentVO {
	private int docNo;
	private String docTitle;
	private String docName;
	private Clob docContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date updateDate;
	private String docStatus;
	private String companyCode;
	private int employeeNo;
	
}
