package com.arion.app.group.main.attendance.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class VacationVO {

	private Integer years;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date startdate;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date enddate;
	private double usevacation;
	private double remainingvacation;
	private double yearsvacation;
	
}
