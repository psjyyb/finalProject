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
}
