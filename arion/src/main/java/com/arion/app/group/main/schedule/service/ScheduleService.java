package com.arion.app.group.main.schedule.service;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
	
	// 사원 일정 조회
	List<ScheduleVO> empScheduleListSelecte(int employeeNo);
	
	// 사원 일정 등록
	Map<String,Object>empScheduleInsert(ScheduleVO scheduleVO); 
	
	// 사원 일정 삭제
	int empScheduleDelete(int scheduleNo);
	
	// 사원 일정 수정
	int empScheduleUpdate(ScheduleVO scheduleVO);
	
	// 부서일정 조회
	List<ScheduleVO> deptScheduleListSelect(int departmentNo);
}
