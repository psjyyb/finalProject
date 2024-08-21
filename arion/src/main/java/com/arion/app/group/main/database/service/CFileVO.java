package com.arion.app.group.main.database.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.main.attendance.service.VacationVO;

import lombok.Data;

@Data
public class CFileVO {

	private Integer me;
	private String companycode;
	private Integer parent;
	private String uploader;
	private String filename;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date uploaddate;
	private Integer csize;
	private String forder;
	private String cname;
	private String departmentno;
	private String rankid;
	private String cform;
	
}
