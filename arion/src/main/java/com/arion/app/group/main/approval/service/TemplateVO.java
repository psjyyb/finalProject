package com.arion.app.group.main.approval.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TemplateVO {
	private String tempNo;
	private String docType;
	private MultipartFile docFileName;
	private String docFile;
	private String docName;
	private String companyCode;
	private String docImg;
	
}
