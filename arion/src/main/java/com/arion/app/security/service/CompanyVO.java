package com.arion.app.security.service;

import lombok.Data;

@Data
public class CompanyVO {
    private String companyCode;
    private String companyName;
    private String companyTel;
    private String companyId;
    private String companyPw;
    private String companyBusinessNumber;
    private String companyAddress;
    private String companyType;
    private int companyPost;
    private String siteResp;
    private String ceoName;
    private String ceoEmail;
    private String ceoPhone;
}
