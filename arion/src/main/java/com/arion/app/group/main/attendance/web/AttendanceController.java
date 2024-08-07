package com.arion.app.group.main.attendance.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.security.service.LoginUserVO;

@Controller
public class AttendanceController {

	@Autowired
	AttendanceService attendanceservice;
	
	@GetMapping("/group/attendance/myattendance")
	public String myattendance(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/myattendance";
	}
	
	@GetMapping("/group/attendance/attendancelist")
	public String attendancelist(Authentication authentication,Model model) {
		LoginUserVO userDetails = (LoginUserVO) authentication.getPrincipal();		
		String companyCode = userDetails.getUserVO().getCompanyCode(); 
		String rankName = userDetails.getUserVO().getRankName(); 
		List<AEmployeeVO> list = attendanceservice.aEmployeeList(companyCode,rankName);
		model.addAttribute("list", list);
		
		return "group/attendance/attendancelist";
	}
	
	@GetMapping("/group/attendance/attendancelist/attendance")
	public String underattendance(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/attendancelist/attendance";
	}
	
	@GetMapping("/group/attendance/myworktime")
	public String myworktime(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/myworktime";
	}
	
	@GetMapping("/group/attendance/worktimelist")
	public String worktimelist(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/worktimelist";
	}
	
	@GetMapping("/group/attendance/worktimelist/worktime")
	public String underworktime(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/worktimelist/worktime";
	}
	
	@GetMapping("/group/attendance/myvacation")
	public String myvacation(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/myvacation";
	}
	
	@GetMapping("/group/attendance/vacationlist")
	public String vacationlist(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/vacationlist";
	}
	
	@GetMapping("/group/attendance/vacationlist/vacation")
	public String undervacation(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/vacationlist/vacation";
	}
	
	@GetMapping("/group/attendance/myvacationdata")
	public String myvacationdata(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/myvacationdata";
	}
	
	@GetMapping("/group/attendance/vacationdatalist")
	public String vacationdatalist(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/vacationdatalist";
	}
	
	@GetMapping("/group/attendance/vacationdatalist/vacationdata")
	public String undervacationdata(Model model) {
//		AttendanceVO myattendance = attendanceservice.myattendance();
//		model.addAttribute("myattendance", myattendance);
		
		return "group/attendance/vacationdatalist/vacationdata";
	}
	
}
