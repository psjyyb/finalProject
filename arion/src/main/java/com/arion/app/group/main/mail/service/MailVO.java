package com.arion.app.group.main.mail.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MailVO {
	private Integer mailNo;
	private Integer empNo;
	private Integer deptNo;
	private Integer rankId;
	private Integer senderId;
	private String mailContent;
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date sendDate;
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date receiveDate;
	private String mailTitle;
	private String mail_status;
	
}
