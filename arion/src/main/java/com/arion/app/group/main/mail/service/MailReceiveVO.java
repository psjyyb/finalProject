package com.arion.app.group.main.mail.service;

import lombok.Data;

@Data
public class MailReceiveVO {
    private int receiveNo;         // 수신자 고유 번호
    private String receiveName;    // 수신자 이름
    private int mailNo;            // 메일 번호 (MailVO와 연결)
    private String employeeId;     // 수신자 사원 ID
    private String receiveEmail;   // 수신자 이메일 (SMTP용)
    private String companyCode;    // 회사 코드
}