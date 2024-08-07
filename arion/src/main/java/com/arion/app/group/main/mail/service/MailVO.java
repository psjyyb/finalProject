package com.arion.app.group.main.mail.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MailVO {
    private int mailNo;
    private String employeeId; 
    private String senderName;
    private Date sendDate;
    private String mailTitle;
    private String mailStatus;
    private String companyCode;
}




