package com.arion.app.group.main.attendance.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.attendance.mapper.AttendanceMapper;
import com.arion.app.group.main.attendance.service.AEmployeeVO;
import com.arion.app.group.main.attendance.service.AttendanceDownloadVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.EmpVacationVO;
import com.arion.app.group.main.attendance.service.SumWorkTimeVO;
import com.arion.app.group.main.attendance.service.VacationVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;
import com.arion.app.group.main.attendance.service.YearsVO;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired 
	private AttendanceMapper attendancemapper;
	
	@Override
	public AEmployeeVO aEmployee(int iemployeeno) {
		
		return attendancemapper.aEmployee(iemployeeno);
	}
	
	
	@Override
	public List<AEmployeeVO> aEmployeeList(String companycode, String rankname) {
		
		return attendancemapper.aEmployeeList(companycode,rankname);
	}
	
	@Override
	public List<AttendanceVO> attendance(int employeeno,String start,String end) {
		
		return attendancemapper.attendance(employeeno,start,end);
	}
	
	@Override
	public List<WorkTimeVO> worktime(int employeeno,String start,String end) {
		
		return attendancemapper.worktime(employeeno,start,end);
	}


	@Override
	public List<AttendanceDownloadVO> attendancedownload(int employeeno,String start,String end) {
		
		return attendancemapper.attendancedownload(employeeno,start,end);
	}


	@Override
	public SumWorkTimeVO sumworktime(int employeeno, String start, String end) {
		
		return attendancemapper.sumworktime(employeeno, start, end);
	}


	@Override
	public List<YearsVO> yearslist(int employeeno) {
		AEmployeeVO aemployee =attendancemapper.aEmployee(employeeno);
		System.out.println(aemployee.getHireDate());
		Date hiredate= aemployee.getHireDate();
		Calendar calendar = Calendar.getInstance();
		
		Date today = new Date();
        int years=0;
		List<YearsVO> yearslist = new ArrayList<YearsVO>();
		
		Date startdate;
		Date enddate;
		
		startdate = hiredate;
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        
        // Date 객체를 문자열로 변환합니다.
        String formattedstartdate ;
        String formattedenddate ;
		
		while(startdate.before(today)) {
			
			calendar.setTime(startdate);
			
			// 365일을 추가합니다.
			calendar.add(Calendar.DAY_OF_YEAR, 365);
			enddate = calendar.getTime();
			formattedstartdate = formatter.format(startdate);
			formattedenddate = formatter.format(enddate);
			System.out.println(formattedstartdate+"-"+formattedenddate+"년수:"+years);
			YearsVO yearsvo = new YearsVO();
			yearsvo.setPeriod(formattedstartdate+"- "+formattedenddate+"("+years+"년차)");
			yearsvo.setYears(years);
			
			yearslist.add(yearsvo);
            calendar.setTime(enddate);
			
			// 365일을 추가합니다.
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			startdate=calendar.getTime();
			years++;
			
			
		}
		yearslist=yearslist.reversed();
		return yearslist;
	}


	@Override
	public List<VacationVO> vacation(int employeeno, int years) {
		AEmployeeVO aemployee =attendancemapper.aEmployee(employeeno);
		Date hiredate= aemployee.getHireDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hiredate);
		calendar.add(Calendar.DAY_OF_YEAR, 365*years+(years)*1);
		Date start = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 365);
		Date end = calendar.getTime();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
		String qstart = df2.format(start);
		String qend = df2.format(end);
		System.out.println(qstart);
		System.out.println(qend);
		
		List<VacationVO> vacation = attendancemapper.vacation(employeeno, qstart, qend);
		
		
		return vacation;
	}


	@Override
	public EmpVacationVO empvacation(int employeeno, int years, List<VacationVO> vacationlist) {
		
		EmpVacationVO empvacation = new EmpVacationVO();
		//총 휴가 개수
		int vacation;
		if(years==0) {
			vacation=12;
		}
		else if(years>=21) {
			vacation=25;
		}
		else {
			if((years%2)==1) {
				vacation=15+(years/2);
			}
			else {
				vacation=15+((years/2)-1);
			}
			
		}
		empvacation.setVacation(Integer.toString(vacation));
		System.out.println(vacation);
		//사용휴가일수
		double used=0;
		
		for(VacationVO va:vacationlist) {
			used+=va.getUsevacation();
		}
		empvacation.setUsed(Double.toString(used));
		System.out.println(used);
		//남은 휴가일수
		double remaining=(double)vacation-used;
		empvacation.setRemaining(Double.toString(remaining));
		System.out.println(remaining);
		//남은 사용기간
		String expirationdate;
		AEmployeeVO aemployee =attendancemapper.aEmployee(employeeno);
		Date hiredate= aemployee.getHireDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hiredate);
		calendar.add(Calendar.DAY_OF_YEAR, 365*years+(years)*1);
		Date start = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 365);
		Date end = calendar.getTime();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
		String qstart = df2.format(start);
		String qend = df2.format(end);
		expirationdate=qstart+"~"+qend ;
		System.out.println(expirationdate);
		
		
		empvacation.setExpirationdate(expirationdate);
		
		return empvacation;
	}
	
}
