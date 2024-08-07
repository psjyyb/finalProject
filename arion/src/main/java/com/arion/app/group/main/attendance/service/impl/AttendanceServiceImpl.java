package com.arion.app.group.main.attendance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.attendance.mapper.AttendanceMapper;
import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired 
	private AttendanceMapper attendancemapper;

	@Override
	public AEmployeeVO aEmployee() {
		// TODO Auto-generated method stub
		return attendancemapper.aEmployee();
	}
	
	
	@Override
	public List<AEmployeeVO> aEmployeeList(String companyCode, String rankName) {
		// TODO Auto-generated method stub
		return attendancemapper.aEmployeeList(companyCode,rankName);
	}
	
	@Override
	public List<AttendanceVO> attendance() {
		// TODO Auto-generated method stub
		return attendancemapper.attendance();
	}
	
	@Override
	public List<WorkTimeVO> worktime() {
		// TODO Auto-generated method stub
		return attendancemapper.worktime();
	}
	
}
