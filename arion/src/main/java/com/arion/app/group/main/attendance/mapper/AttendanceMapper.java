package com.arion.app.group.main.attendance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceDownloadVO;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.SumWorkTimeVO;
import com.arion.app.group.main.attendance.service.VacationVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;

@Mapper
public interface AttendanceMapper {
	
	public AEmployeeVO aEmployee(int iemployeeno);
	
	public List<AEmployeeVO> aEmployeeList(String companycode, String rankname);
	
	public List<AttendanceVO> attendance(int employeeno,String start, String end);
	
	public List<WorkTimeVO> worktime(int employeeno, String start, String end);
	
	public SumWorkTimeVO sumworktime(int employeeno, String start, String end);
	
	public List<AttendanceDownloadVO> attendancedownload(int employeeno,String start, String end);

	public List<VacationVO> vacation(int employeeno, String start, String end);
}
