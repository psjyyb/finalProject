package com.arion.app.group.main.mail.service;

import lombok.Data;

@Data
public class MailReceiveVO {
    private int receiveNo;
    private String receiveName;
    private int mailNo;
    private String employeeId;
    private int fileNo;
    private String companyCode;
}
