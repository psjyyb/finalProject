package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.board.service.BoardVO;

import lombok.Data;

@Data
public class AEmployeeVO {

	private Integer employeeno;
	private String employeename;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date hireDate;
	private String employeeresp;	
	private String departmentname;
	private String rankname;
	private String companycode;
	
}
