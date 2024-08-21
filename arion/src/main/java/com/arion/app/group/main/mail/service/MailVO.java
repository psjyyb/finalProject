package com.arion.app.group.main.mail.service;


import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MailVO {
    private int mailNo;
    private String senderId;
    private String senderName;   
    private String receiverId;     
    private String[] receiverIds;  
    private String mailTitle;
    private String mailContent;
    private String mailStatus;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sendDate;
    
    private String companyCode;
	private int fileNo;				 //파일번호
	private String fileName;		 //파일이름
	private String fileoriginalName; //파일원본이름
	private String filePath;		 //파일경로
	private int fileTurn;			 //파일순서
	private String fileType;		 //파일타입
	private String tableName;		 //테이블이름
	private int keyNo;				 //키번호
    
}