package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.board.service.BoardVO;

import lombok.Data;

@Data
public class AttendanceVO {	
	
	private String starttime;	
	private String endtime;
	private String state;	
	
	private String attdate;
	private String halfoff;	
}
