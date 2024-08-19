package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class VacationVO {

	private String vacationdate;
	private String holtype;
	private double usevacation;
	private String docstatus;
	private String createdate;
	
}
