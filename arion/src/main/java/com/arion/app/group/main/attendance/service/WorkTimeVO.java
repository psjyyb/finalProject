package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.board.service.BoardVO;

import lombok.Data;

@Data
public class WorkTimeVO {

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date workdate;
	private String employeename;
	private Integer worktime;
	
	
}
