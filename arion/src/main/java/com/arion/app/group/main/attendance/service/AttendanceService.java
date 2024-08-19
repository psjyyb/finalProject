package com.arion.app.group.main.attendance.service;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

	public AEmployeeVO aEmployee(int iemployeeno);
	
	public List<AEmployeeVO> aEmployeeList(String companycode, String rankname);
	
	public List<AttendanceVO> attendance(int employeeno,String start,String end);

	public List<AttendanceDownloadVO> attendancedownload(int iemployeeno, String qstart, String qend);

	public List<WorkTimeVO> worktime(int employeeno, String start, String end);
	
	public SumWorkTimeVO sumworktime(int employeeno, String start, String end);
	
	//년수별 기간 
	public List<YearsVO> yearslist(int employeeno);
	//년수로 해당 년수 휴가기록
	public List<VacationVO> vacation(int employeeno,int years);
	
	public EmpVacationVO empvacation(int employeeno,int years,List<VacationVO> vacationlist);
}
