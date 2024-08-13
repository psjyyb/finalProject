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
    private String receiverId;
    private String mailTitle;
    private String mailContent;
    private String mailStatus;
    @DateTimeFormat(pattern="yyyy-MM-dd")//파마리터자동변환
    private Date sendDate;
    
    private String companyCode;

    // 검색 조건 추가
    private Date startDate;       // 검색 시작 날짜
    private Date endDate;         // 검색 종료 날짜
    private String searchTitle;   // 제목으로 검색
    private String searchContent; // 내용으로 검색
    private String searchSender;  // 작성자로 검색

}