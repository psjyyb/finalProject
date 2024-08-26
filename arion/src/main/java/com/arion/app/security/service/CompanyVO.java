package com.arion.app.security.service;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CompanyVO {
    private String companyCode;
    private String companyName;
    private String companyTel;
    private String companyId;
    private String companyPw;
    private long companyBusinessNumber;
    private String companyAddress;
    private String companyType;
    private int companyPost;
    private String siteResp;
    private String ceoName;
    private String ceoEmail;
    private String ceoPhone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finalDate;
}
