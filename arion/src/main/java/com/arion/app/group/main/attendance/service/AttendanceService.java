package com.arion.app.group.main.attendance.service;

import java.util.List;

public interface AttendanceService {

	public AEmployeeVO aEmployee();
	
	public List<AEmployeeVO> aEmployeeList(String companycode, String rankname);
	
	public List<AttendanceVO> attendance(int employeeno,String start,String end);
	
	public List<WorkTimeVO> worktime();

	public List<AttendanceDownloadVO> attendancedownload(int iemployeeno, String qstart, String qend);
}
