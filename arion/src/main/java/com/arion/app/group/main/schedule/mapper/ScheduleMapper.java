package com.arion.app.group.main.schedule.mapper;

import java.util.List;

import com.arion.app.group.main.schedule.service.ScheduleVO;

public interface ScheduleMapper {

	// 사원 일정 조회
	List<ScheduleVO> selecteEmpScheduleList(int employeeNo);
	
	// 사원 일정 등록
	int insertEmpSchedule(ScheduleVO scheduleVO);
	
	// 사원 일정 삭제
	int deleteEmpSchedule(int scheduleNo);
	
	// 사원 일정 수정
	int updateEmpSchedule(ScheduleVO scheduleVO);
}
