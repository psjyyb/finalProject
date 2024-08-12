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
    public void download(HttpServletResponse res,@RequestParam(value = "employeeno",required = false) String employeeno,
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
        String headerNames[] = new String[]{"날짜", "출근시간", "퇴근시간","상태"};

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
            bodyRow = sheet.createRow(rowCount++);
            
            SimpleDateFormat df3 = new SimpleDateFormat("yy/MM/dd");
            String attdate = df3.format(attendancedownload.getAttdate());
                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(attdate); // 데이터 추가
                
                System.out.println(attendancedownload.getAttdate());
                                             
                bodyCell = bodyRow.createCell(1);
                bodyCell.setCellValue(attendancedownload.getStarttime()); // 데이터 추가                               
                bodyCell = bodyRow.createCell(2);
                bodyCell.setCellValue(attendancedownload.getEndtime()); // 데이터 추가                                
                bodyCell = bodyRow.createCell(3);
                bodyCell.setCellValue(attendancedownload.getState()); // 데이터 추가                          
        }

        String fileName = "spring_excel_download";

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");       
        workbook.write(res.getOutputStream());
        workbook.close();
        
    }
}