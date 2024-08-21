package com.arion.app.group.main.schedule.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.schedule.service.ScheduleService;
import com.arion.app.group.main.schedule.service.ScheduleVO;

@Controller
public class ScheduleController {
	
	private ScheduleService scheduleService;
	
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	@GetMapping("/group/schedule/calendar")
	public String ScheduleCalendar() {		
		return"/group/schedule/calendar";
	}
	@GetMapping("/group/schedule/deptCalendar")
	public String ScheduleDeptCalendar() {		
		return"/group/schedule/deptCalendar";
	}
	@PostMapping("/group/schedule/empCalcInsert")
	@ResponseBody
	public Map<String,Object> empCalcInsert(ScheduleVO scheduleVO,HttpSession session){
		String comCode = (String) session.getAttribute("companyCode");
		int empNo = (Integer) session.getAttribute("employeeNo");
		scheduleVO.setCompanyCode(comCode);
		scheduleVO.setEmployeeNo(empNo);
		return scheduleService.empScheduleInsert(scheduleVO);
	}
	@GetMapping("/group/schedule/empCalendar")
	@ResponseBody
	public List<ScheduleVO> empScheduleList(HttpSession session, Model model){
		int empNo = (Integer) session.getAttribute("employeeNo");
		return scheduleService.empScheduleListSelecte(empNo);
	}
	@PostMapping("/group/schedule/empCalcDelete")
	@ResponseBody
	public int empCaleDelete(@RequestParam int scheduleNo) {
		return scheduleService.empScheduleDelete(scheduleNo);
	}
	@PostMapping("/group/schedule/empCalcUpdate")
	@ResponseBody
	public int empCalcUpdate(ScheduleVO scheduleVO) {
		return scheduleService.empScheduleUpdate(scheduleVO);
	}
}