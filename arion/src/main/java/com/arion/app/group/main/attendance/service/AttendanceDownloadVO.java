package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class AttendanceDownloadVO {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date attdate;
	private String starttime;	
	private String endtime;
	private String state;	
}
