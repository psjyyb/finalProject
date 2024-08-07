package com.arion.app.group.main.attendance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;

@Mapper
public interface AttendanceMapper {
	
	public AEmployeeVO aEmployee();
	
	public List<AEmployeeVO> aEmployeeList(String companycode, String rankname);
	
	public List<AttendanceVO> attendance();
	
	public List<WorkTimeVO> worktime();
	
	
}