package com.arion.app.group.main.schedule.service;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScheduleVO {
	private int scheduleNo;
	private String scheduleTitle;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	private LocalDate scheduleStart;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	private LocalDate scheduleEnd;
	private int employeeNo;
	private String companyCode;
	private int departmentsNo;
	private String scheduleStarts;
	private String scheduleEnds;
}
