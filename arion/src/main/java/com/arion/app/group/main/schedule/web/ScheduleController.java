package com.arion.app.group.main.schedule.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.schedule.service.ScheduleVO;

@Controller
public class ScheduleController {
	
	@GetMapping("/group/schedule/calendar")
	public String ScheduleCalendar() {
		
		return"/group/schedule/calendar";
	}
	@PostMapping("/group/schedule/empCalcInsert")
	@ResponseBody
	public Map<String,Object> empCalcInsert(ScheduleVO scheduleVO,HttpSession session){
		String comCode = (String) session.getAttribute("companyCode");
		int empNo = (Integer) session.getAttribute("employeeNo");
		System.out.println(scheduleVO);
	return null;
	}
}
