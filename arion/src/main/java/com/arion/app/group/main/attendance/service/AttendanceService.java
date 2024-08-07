package com.arion.app.group.main.attendance.service;

import java.util.List;

public interface AttendanceService {

	public AEmployeeVO aEmployee();
	
	public List<AEmployeeVO> aEmployeeList(String companyCode, String rankName);
	
	public List<AttendanceVO> attendance();
	
	public List<WorkTimeVO> worktime();
}
