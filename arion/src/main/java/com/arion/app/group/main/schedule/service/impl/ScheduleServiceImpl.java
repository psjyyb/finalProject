package com.arion.app.group.main.schedule.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.arion.app.group.main.schedule.mapper.ScheduleMapper;
import com.arion.app.group.main.schedule.service.ScheduleService;
import com.arion.app.group.main.schedule.service.ScheduleVO;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	private ScheduleMapper scheduleMapper;
	
	public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}
	
	@Override
	public Map<String, Object> empScheduleInsert(ScheduleVO scheduleVO) {
		Map<String,Object> map = new HashMap<>();
		if(scheduleVO.getScheduleEnds()=="") {
			scheduleVO.setScheduleEnds(scheduleVO.getScheduleStarts());
		}
		int result = scheduleMapper.insertEmpSchedule(scheduleVO);
		boolean isSuccess = false;
		if(result > 0 ) {
			isSuccess = true;
		}
		map.put("result", isSuccess);
		map.put("target", scheduleVO);
		return map;
	}
	@Override
	public List<ScheduleVO> empScheduleListSelecte(int employeeNo) {
		return scheduleMapper.selecteEmpScheduleList(employeeNo);
	}
	@Override
	public int empScheduleDelete(int scheduleNo) {
		return scheduleMapper.deleteEmpSchedule(scheduleNo);
	}
	@Override
	public int empScheduleUpdate(ScheduleVO scheduleVO) {
		if(scheduleVO.getScheduleEnds()=="") {
			scheduleVO.setScheduleEnds(scheduleVO.getScheduleStarts());
		}
		return scheduleMapper.updateEmpSchedule(scheduleVO);
	}
	@Override
	public List<ScheduleVO> deptScheduleListSelect(String departmentName,String companyCode) {
		return scheduleMapper.selectDeptScheduleList(departmentName,companyCode);
	}

}
