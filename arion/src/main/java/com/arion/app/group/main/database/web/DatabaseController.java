package com.arion.app.group.main.database.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.DatabaseService;
import com.arion.app.group.main.database.service.UnderRankVO;

@Controller
@RequestMapping("/group/database")
public class DatabaseController {

	@Autowired
	DatabaseService databaseservice;
	
	//데이터베이스 페이지이동
		@GetMapping("/database")
		public String database(HttpServletRequest request,Model model) {
			
			HttpSession session = request.getSession();			
			String companyCode = (String)session.getAttribute("companyCode");
			String rankName = (String)session.getAttribute("rankName");
			int ranking= databaseservice.ranking(companyCode, rankName);
			List<UnderRankVO> UnderRank= databaseservice.underank(ranking);
		
			model.addAttribute("underranks", UnderRank);
			model.addAttribute("ranking", ranking);
			return "group/database/database";
		}
		
		
		       //ajax db 첫접속
				@RequestMapping("/start")
				@ResponseBody
				public Map<String, Object> start(HttpServletRequest request,
						@RequestParam(value = "companycode",required = false) String companycode) throws Exception {
					
					List<CFileVO> startforder= databaseservice.startforder(companycode);				
					System.out.println(startforder);
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("startforder", startforder);
					return result;
				}
				
				//ajax db 파일 호출
				@RequestMapping("/callfile")
				@ResponseBody
				public Map<String, Object> callfile(HttpServletRequest request,
						@RequestParam(value = "companycode",required = false) String companycode,
						@RequestParam(value = "parent",required = false) int parent) throws Exception {
					
					List<CFileVO> filelist= databaseservice.filelist(companycode,parent);
					
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("filelist", filelist);
					return result;
				}		
				
				
				
				
}
