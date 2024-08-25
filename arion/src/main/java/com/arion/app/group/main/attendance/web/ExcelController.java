package com.arion.app.group.main.attendance.web;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.arion.app.group.main.attendance.service.AttendanceDownloadVO;
import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.attendance.service.AttendanceVO;
import com.arion.app.group.main.attendance.service.EmpVacationVO;
import com.arion.app.group.main.attendance.service.SumWorkTimeVO;
import com.arion.app.group.main.attendance.service.VacationVO;
import com.arion.app.group.main.attendance.service.WorkTimeVO;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

	@Autowired
	AttendanceService attendanceservice;
	
	@PostMapping("/files/attendancedownload")
    public void attendancedownload(HttpServletResponse res,@RequestParam(value = "employeeno",required = false) String employeeno,
    		@RequestParam(value = "startdate",required = false) String startdate,@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
    	int iemployeeno=Integer.parseInt(employeeno);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date start = df.parse(startdate);
		 Date end = df.parse(enddate);	
		 SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			String qstart = df2.format(start);
			String qend = df2.format(end);
			List<AttendanceDownloadVO> attendancedownloads = attendanceservice.attendancedownload(iemployeeno,qstart,qend);
    	
        Workbook workbook =new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(); // 엑셀 sheet 이름
        
        int rowCount = 0; // 데이터가 저장될 행

        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(rowCount++);
        
            headerCell = headerRow.createCell(0);
            headerCell.setCellValue("날짜"); // 데이터 추가
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("출근시간"); // 데이터 추가
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("퇴근시간"); // 데이터 추가
            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("상태"); // 데이터 추가

        Row bodyRow = null;
        Cell bodyCell = null;

        for(AttendanceDownloadVO attendancedownload : attendancedownloads) {
        	System.out.println(attendancedownload.getAttdate());
            bodyRow = sheet.createRow(rowCount++);
            
            SimpleDateFormat df3 = new SimpleDateFormat("yy/MM/dd");
            String attdate = df3.format(attendancedownload.getAttdate());
                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(attdate); // 데이터 추가                                      
                bodyCell = bodyRow.createCell(1);
                if(attendancedownload.getStarttime()!=null) {
                bodyCell.setCellValue(attendancedownload.getStarttime()); // 데이터 추가                               
                }
                bodyCell = bodyRow.createCell(2);
                if(attendancedownload.getEndtime()!=null) {
                bodyCell.setCellValue(attendancedownload.getEndtime());
                }// 데이터 추가                                
                bodyCell = bodyRow.createCell(3);
                bodyCell.setCellValue(attendancedownload.getState()); // 데이터 추가                          
        }

        String fileName = "spring_excel_download";

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");       
        workbook.write(res.getOutputStream());
        workbook.close();
        
    }
	
	
	@PostMapping("/files/workdownload")
    public void workdownload(HttpServletResponse res,@RequestParam(value = "employeeno",required = false) String employeeno,
    		@RequestParam(value = "startdate",required = false) String startdate,@RequestParam(value = "enddate",required = false) String enddate) throws Exception {
    	int iemployeeno=Integer.parseInt(employeeno);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date start = df.parse(startdate);
		 Date end = df.parse(enddate);	
		 SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			String qstart = df2.format(start);
			String qend = df2.format(end);
			List<WorkTimeVO> worktimelist = attendanceservice.worktime(iemployeeno,qstart,qend);
			SumWorkTimeVO sumworktime = attendanceservice.sumworktime(iemployeeno, qstart, qend);
			
        Workbook workbook =new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(); // 엑셀 sheet 이름
        
        int rowCount = 0; // 데이터가 저장될 행


        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(rowCount++);
        
            headerCell = headerRow.createCell(0);
            headerCell.setCellValue("일자"); // 데이터 추가
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("근무시간(시간)"); // 데이터 추가
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("근무시간(분)"); // 데이터 추가
            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("표준시간(시간)"); // 데이터 추가
            headerCell = headerRow.createCell(4);
            headerCell.setCellValue("표준시간(분)"); // 데이터 추가
            headerCell = headerRow.createCell(5);
            headerCell.setCellValue("차이(시간)"); // 데이터 추가
            headerCell = headerRow.createCell(6);
            headerCell.setCellValue("차이(분)"); // 데이터 추가
        Row bodyRow = null;
        Cell bodyCell = null;

        for(WorkTimeVO worktime : worktimelist) {
        	System.out.println(worktime.getAttdate());
            bodyRow = sheet.createRow(rowCount++);
            
            SimpleDateFormat df3 = new SimpleDateFormat("yy/MM/dd");
            String attdate = df3.format(worktime.getAttdate());
                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(attdate); // 데이터 추가                    
                bodyCell = bodyRow.createCell(1);
                bodyCell.setCellValue(worktime.getWorktimehour()); // 데이터 추가    
                bodyCell = bodyRow.createCell(2);
                bodyCell.setCellValue(worktime.getWorktimeminute()); // 데이터 추가                               
                bodyCell = bodyRow.createCell(3);
                bodyCell.setCellValue(worktime.getStandardworktimehour()); // 데이터 추가                                
                bodyCell = bodyRow.createCell(4);
                bodyCell.setCellValue(worktime.getStandardworktimeminute()); // 데이터 추가  
                bodyCell = bodyRow.createCell(5);
                bodyCell.setCellValue(worktime.getIntervalhour()); // 데이터 추가   
                bodyCell = bodyRow.createCell(6);
                bodyCell.setCellValue(worktime.getIntervalminute()); // 데이터 추가   
        }
        bodyRow = sheet.createRow(rowCount++);
        bodyCell = bodyRow.createCell(0);
        bodyCell.setCellValue("총합"); // 데이터 추가                    
        bodyCell = bodyRow.createCell(1);
        bodyCell.setCellValue(sumworktime.getSumworktimehour()); // 데이터 추가    
                                   
        bodyCell = bodyRow.createCell(2);
        bodyCell.setCellValue(sumworktime.getSumworktimeminute()); // 데이터 추가                                
        
        
        bodyCell = bodyRow.createCell(5);
        bodyCell.setCellValue(sumworktime.getSumintervalhour()); // 데이터 추가   
        bodyCell = bodyRow.createCell(6);
        bodyCell.setCellValue(sumworktime.getSumintervalminute()); // 데이터 추가   
        
        

        String fileName = "spring_excel_download";

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");       
        workbook.write(res.getOutputStream());
        workbook.close();
        
    }
	
	@PostMapping("/files/vacationdownload")
    public void vacationdownload(HttpServletResponse res,@RequestParam(value = "employeeno",required = false) String employeeno,
    		@RequestParam(value = "years",required = false) String years) throws Exception {
    	int iemployeeno=Integer.parseInt(employeeno);
    	int iyears=Integer.parseInt(years);
    	
			List<VacationVO> vacationlist = attendanceservice.vacation(iemployeeno,iyears);
			EmpVacationVO empvacation = attendanceservice.empvacation(iemployeeno, iyears, vacationlist);
			
        Workbook workbook =new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(); // 엑셀 sheet 이름
        
        int rowCount = 0; // 데이터가 저장될 행


        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(rowCount++);
       

         
        
        sheet.setColumnWidth(0, 6800);
        sheet.setColumnWidth(4, 5000);
            headerCell = headerRow.createCell(0);
            headerCell.setCellValue("일자"); // 데이터 추가
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("휴가유형"); // 데이터 추가
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("휴가일수"); // 데이터 추가
            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("상태"); // 데이터 추가
            headerCell = headerRow.createCell(4);
            headerCell.setCellValue("신청일자"); // 데이터 추가
            
            
        Row bodyRow = null;
        Cell bodyCell = null;

        for(VacationVO vacation : vacationlist) {
        	
            bodyRow = sheet.createRow(rowCount++);
            
                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(vacation.getVacationdate()); // 데이터 추가                    
                bodyCell = bodyRow.createCell(1);
                bodyCell.setCellValue(vacation.getHoltype()); // 데이터 추가    
                bodyCell = bodyRow.createCell(2);
                bodyCell.setCellValue(vacation.getUsevacation()); // 데이터 추가                               
                bodyCell = bodyRow.createCell(3);
                bodyCell.setCellValue(vacation.getDocstatus()); // 데이터 추가                                
                bodyCell = bodyRow.createCell(4);
                bodyCell.setCellValue(vacation.getCreatedate()); // 데이터 추가  
                
        }
        bodyRow = sheet.createRow(rowCount++);
        bodyCell = bodyRow.createCell(0);
        bodyCell.setCellValue("사용일수"); // 데이터 추가                    
        bodyCell = bodyRow.createCell(1);
        bodyCell.setCellValue(empvacation.getUsed()); // 데이터 추가                                       
        bodyCell = bodyRow.createCell(2);
        bodyCell.setCellValue("남은일수"); // 데이터 추가                                                
        bodyCell = bodyRow.createCell(3);
        bodyCell.setCellValue(empvacation.getRemaining()); // 데이터 추가   
        bodyCell = bodyRow.createCell(4);
        bodyCell.setCellValue("휴가일수"); // 데이터 추가  
        bodyCell = bodyRow.createCell(5);
        bodyCell.setCellValue(empvacation.getVacation()); // 데이터 추가  
        bodyCell = bodyRow.createCell(6);
        bodyCell.setCellValue("사용기한"); // 데이터 추가  
        bodyCell = bodyRow.createCell(7);
        bodyCell.setCellValue(empvacation.getExpirationdate()); // 데이터 추가  

        String fileName = "spring_excel_download";

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");       
        workbook.write(res.getOutputStream());
        workbook.close();
        
    }
	
}