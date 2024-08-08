package com.arion.app.group.main.attendance.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.security.service.LoginUserVO;

@Controller
public class AttendanceController {

	@Autowired
	AttendanceService attendanceservice;
	
	@RequestMapping("/attendancelist")
	@ResponseBody
	public Map<String, Object> getattendancelist(HttpServletRequest request,@RequestParam(value = "startdate",required = false) String startdate,
			@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
		HttpSession session = request.getSession();
		int EmployeeNo = (int)session.getAttribute("EmployeeNo");
		//Date startdate = date
		//Date enddate = 
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date start = df.parse(startdate);
		 Date end = df.parse(enddate);	   
		System.out.println(start);
		System.out.println(end);
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
		String qstart = df2.format(start);
		String qend = df2.format(end);
		System.out.println(qstart);
		System.out.println(qend);
		List<AttendanceVO> attendance = attendanceservice.attendance(EmployeeNo,qstart,qend);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("attendancelist", attendance);
		return result;
	}
	
	@GetMapping("/group/attendance/myattendance")
	public String myattendance(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		int EmployeeNo = (int)session.getAttribute("EmployeeNo");
		//List<AttendanceVO> attendance = attendanceservice.attendance(EmployeeNo);
		//model.addAttribute("attendance", attendance );
		
		return "group/attendance/myattendance";
	}
	
	@GetMapping("/group/attendance/attendancelist")
	public String attendancelist(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();			
		String companyCode = (String)session.getAttribute("companyCode");
		String rankName = (String)session.getAttribute("rankName");
		List<AEmployeeVO> alist = attendanceservice.aEmployeeList(companyCode,rankName);
		model.addAttribute("alist", alist);
		
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
