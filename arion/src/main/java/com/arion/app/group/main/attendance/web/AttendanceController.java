package com.arion.app.group.main.attendance.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.EmpVacationVO;
import com.arion.app.group.main.attendance.service.SumWorkTimeVO;
import com.arion.app.group.main.attendance.service.VacationVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;
import com.arion.app.group.main.attendance.service.YearsVO;
import com.arion.app.security.service.LoginUserVO;


@Controller
@RequestMapping("/group/attendance")
public class AttendanceController {

	@Autowired
	AttendanceService attendanceservice;
	
	//ajax 근태기록
	@RequestMapping("/attendancelist")
	@ResponseBody
	public Map<String, Object> getattendancelist(HttpServletRequest request,
			@RequestParam(value = "employeeno",required = false)  Integer EmployeeNo,
			@RequestParam(value = "startdate",required = false) String startdate,
			@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
		
		
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
		return Collections.singletonMap("attendancelist", attendance);
	}
	
	//근태기록 페이지이동
	@GetMapping("/attendances")
	public String attendance(HttpServletRequest request,Model model) {
		
		return "group/attendance/myattendance";
	}
	
	@GetMapping("/attendancelist")
	public String attendancelist(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();			
		String companyCode = (String)session.getAttribute("companyCode");
		String rankName = (String)session.getAttribute("rankName");
		List<AEmployeeVO> emplist = attendanceservice.aEmployeeList(companyCode,rankName);
		model.addAttribute("emplist", emplist);
		model.addAttribute("emplistcount", emplist.size());
		
		
		return "group/attendance/attendancelist";
	}

	
	@GetMapping("/underlingattendancelist/{employeeNo}")
	public String underlingattendancelist(@PathVariable String employeeNo,Model model) {
		
		model.addAttribute("employeeNo", employeeNo);
		System.out.println(employeeNo);
		
		int EmployeeNo = Integer.parseInt(employeeNo);
		AEmployeeVO underlingname= attendanceservice.aEmployee(EmployeeNo);
		System.out.println(underlingname.getEmployeename());
		
		model.addAttribute("underlingName", underlingname.getEmployeename());
		
		
		return "group/attendance/underlingattendance";
	}
	
	
	@GetMapping("/worktime")
	public String worktime(HttpServletRequest request,Model model) {		
		
		return "group/attendance/myworktime";
	}
		
	//ajax 근무시간
		@RequestMapping("/worklist")
		@ResponseBody
		public Map<String, Object> worklist(HttpServletRequest request,
				@RequestParam(value = "employeeno",required = false) Integer EmployeeNo,
				@RequestParam(value = "startdate",required = false) String startdate,
				@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
			
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
			
			List<WorkTimeVO> worktimelist = attendanceservice.worktime(EmployeeNo,qstart,qend);
			SumWorkTimeVO sumworktime = attendanceservice.sumworktime(EmployeeNo, qstart, qend);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("worktimelist", worktimelist);
			result.put("sumworktime", sumworktime);
			
			return result;
		}
	
		
		@GetMapping("/worktimelist")
		public String worktimelist(HttpServletRequest request,Model model) {
			HttpSession session = request.getSession();			
			String companyCode = (String)session.getAttribute("companyCode");
			String rankName = (String)session.getAttribute("rankName");
			List<AEmployeeVO> emplist = attendanceservice.aEmployeeList(companyCode,rankName);
			model.addAttribute("emplist", emplist);
			model.addAttribute("emplistcount", emplist.size());
			
			
			return "group/attendance/worktimelist";
		}
		
		@GetMapping("/underlingworktimelist/{employeeNo}")
		public String underlingworktimelist(@PathVariable String employeeNo,Model model) {
			
			model.addAttribute("employeeNo", employeeNo);
			System.out.println(employeeNo);
			
			int EmployeeNo = Integer.parseInt(employeeNo);
			AEmployeeVO underlingname= attendanceservice.aEmployee(EmployeeNo);
			System.out.println(underlingname.getEmployeename());
			
			model.addAttribute("underlingName", underlingname.getEmployeename());
			
			
			return "group/attendance/underlingworktime";
		}
		
		//휴가
		@GetMapping("/vacation")
		public String vacation(HttpServletRequest request,Model model) {
			
			HttpSession session = request.getSession();
			int EmployeeNo = (int)session.getAttribute("employeeNo");
			List<YearsVO> YearsList=attendanceservice.yearslist(EmployeeNo);
			model.addAttribute("employeeNo", EmployeeNo);
			model.addAttribute("yearsList", YearsList);
			
			return "group/attendance/myvacation";
		}
		
		//ajax 휴가기록
		@RequestMapping("/vacationlist")
		@ResponseBody
		public Map<String, Object> vacationlist(HttpServletRequest request,
				@RequestParam(value = "employeeno",required = false) String employeeno,
				@RequestParam(value = "years",required = false) int years) throws Exception {
						
			int EmployeeNo = Integer.parseInt(employeeno);
			System.out.println(years);

			List<VacationVO>  vacationlist =  attendanceservice.vacation(EmployeeNo, years);
			EmpVacationVO empvacation = attendanceservice.empvacation(EmployeeNo, years, vacationlist);
			
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("vacationlist", vacationlist);
			result.put("empvacation", empvacation);
			return result;
		}
		
		
		@GetMapping("/underlingvacationlist")
		public String underlingvacationlist(HttpServletRequest request,Model model) {
			HttpSession session = request.getSession();			
			String companyCode = (String)session.getAttribute("companyCode");
			String rankName = (String)session.getAttribute("rankName");
			List<AEmployeeVO> emplist = attendanceservice.aEmployeeList(companyCode,rankName);
			model.addAttribute("emplist", emplist);
			model.addAttribute("emplistcount", emplist.size());
			
			
			return "group/attendance/vacationlist";
		}
		
		@GetMapping("/underlingvacationlist/{employeeNo}")
		public String underlingvacationlist(@PathVariable String employeeNo,Model model) {
			
			model.addAttribute("employeeNo", employeeNo);
			System.out.println(employeeNo);
			
			int EmployeeNo = Integer.parseInt(employeeNo);
			AEmployeeVO underlingname= attendanceservice.aEmployee(EmployeeNo);
			System.out.println(underlingname.getEmployeename());
			
			model.addAttribute("underlingName", underlingname.getEmployeename());
			
			List<YearsVO> YearsList=attendanceservice.yearslist(EmployeeNo);
			
			model.addAttribute("yearsList", YearsList);
			
			
			return "group/attendance/underlingvacation";
		}
		
		
		//차트
				@GetMapping("/chart")
				public String chart(HttpServletRequest request,Model model) {						
					
					return "group/attendance/mychart";
				}
				
	    //ajax 차트기록	
				@RequestMapping("/chartlist")
				@ResponseBody
				public Map<String, Object> chartlist(HttpServletRequest request,
						@RequestParam(value = "employeeno",required = false) Integer EmployeeNo,
						@RequestParam(value = "startdate",required = false) String startdate,
						@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
								
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
					
					List<WorkTimeVO> worktimelist = attendanceservice.worktime(EmployeeNo,qstart,qend);

					
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("worktimelist", worktimelist);
					return result;
				}	
				@GetMapping("/underlingchartlist")
				public String underlingchartlist(HttpServletRequest request,Model model) {
					HttpSession session = request.getSession();			
					String companyCode = (String)session.getAttribute("companyCode");
					String rankName = (String)session.getAttribute("rankName");
					List<AEmployeeVO> emplist = attendanceservice.aEmployeeList(companyCode,rankName);
					model.addAttribute("emplist", emplist);
					model.addAttribute("emplistcount", emplist.size());
					
					
					return "group/attendance/chartlist";
				}
				
				@GetMapping("/underlingchartlist/{employeeNo}")
				public String underlingchartlist(@PathVariable String employeeNo,Model model) {
					
					model.addAttribute("employeeNo", employeeNo);
					System.out.println(employeeNo);
					
					int EmployeeNo = Integer.parseInt(employeeNo);
					AEmployeeVO underlingname= attendanceservice.aEmployee(EmployeeNo);
					System.out.println(underlingname.getEmployeename());
					
					model.addAttribute("underlingName", underlingname.getEmployeename());
					
					List<YearsVO> YearsList=attendanceservice.yearslist(EmployeeNo);
					
					model.addAttribute("yearsList", YearsList);
					
					
					return "group/attendance/underlingchart";
				}
				
}
