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
    private String receiverEmail;  // 단일 수신자 이메일(이전)
    private List<String> receiverEmails;  // 다중 수신자 이메일 리스트(외부 수신자)
    private String receiverId;     // 단일 수신자 ID(이전)
    private List<String> receiverIds;  // 다중 수신자 ID 리스트(내부 수신자)
    private String mailTitle;
    private String mailContent;
    private String mailStatus;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sendDate;
    
    private String companyCode;
}