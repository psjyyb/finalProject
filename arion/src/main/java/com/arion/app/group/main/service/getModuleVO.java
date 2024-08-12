package com.arion.app.group.main.service;

import java.util.List;

import lombok.Data;

@Data
public class getModuleVO {
	private String employeeId;
	private String companyCode;
	private Integer moduleNo;
    private String moduleName;

    private List<SubModuleVO> subModules;

}
