package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.board.service.BoardVO;

import lombok.Data;

@Data
public class AttendanceVO {
	
	private Integer attno;
	private Integer employeeno;	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date starttime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endtime;
	private String state;	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date attdate;
	private String halfoff;	
}
