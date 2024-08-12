package com.arion.app.group.main.mail.service;


import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class MailVO {
    private int mailNo;
    private String senderId;
    private String senderName;
    private String receiverEmail;
    private String mailTitle;
    private String mailContent;
    private String mailStatus;
    @DateTimeFormat(pattern="yyyy-MM-dd")//파마리터자동변환
    private Date sendDate;
    
    private String companyCode;

    // Getters and Setters
}