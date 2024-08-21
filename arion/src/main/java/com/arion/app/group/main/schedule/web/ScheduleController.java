package com.arion.app.group.main.schedule.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {
	
	@GetMapping("/group/schedule/calendar")
	public String ScheduleCalendar() {
		
		return"/group/schedule/calendar";
	}
}
