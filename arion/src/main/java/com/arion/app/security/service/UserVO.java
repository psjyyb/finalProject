package com.arion.app.security.service;

import lombok.Data;

@Data
public class UserVO {
    private String companyCode;
    private String loginId;
    private String loginPw;
    private String siteResp;
    private String employeeResp;
    private String loginType;
    private String departmentName;
    private String rankName;
    private String empName;
    private int employeeNo;
    private String ceoName;
    

}
