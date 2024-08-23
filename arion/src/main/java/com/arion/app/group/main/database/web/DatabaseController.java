package com.arion.app.group.main.database.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.main.attendance.service.AttendanceService;
import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.DatabaseService;
import com.arion.app.group.main.database.service.UnderRankVO;
import com.arion.app.home.board.service.HomeQnaVO;

@Controller
@RequestMapping("/group/database")
public class DatabaseController {

	@Value("${file.upload.path}")
	private String uploadPath;
	
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
				
				//ajax db 폴더 생성
				@RequestMapping("/forderupload")
				@ResponseBody
				public Map<String, Object> forderupload(HttpServletRequest request,
						@RequestParam(value = "companycode",required = false) String companycode,
						@RequestParam(value = "parent",required = false) int parent,
						@RequestParam(value = "uploader",required = false) String uploader,
						@RequestParam(value = "rankname",required = false) String rankname,
						@RequestParam(value = "filename",required = false) String filename
						) throws Exception {
					System.out.println("폴더생성");
				
					int forder= databaseservice.forderupload(companycode, parent, uploader, rankname, filename);
					if(forder==1) {
						
						System.out.println("폴더생성성공");
						
					}
					
					List<CFileVO> returnforder= databaseservice.forder(companycode);
					
					
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("forder", forder);
					result.put("returnforder", returnforder);
					return result;
				}		
				//ajax 파일 업로드
				@RequestMapping(value = "/fileupload")
				@ResponseBody
			    public Map<String, Object> fileupload(HttpServletRequest request,
			    		@RequestParam(value = "parent",required = false) int parent,
			    		@RequestParam(value ="files",required = false) List<MultipartFile> multipartFile) {
					HttpSession session = request.getSession();			
					String companycode = (String)session.getAttribute("companyCode");
					String uploader = (String)session.getAttribute("empName");
					String rankname = (String)session.getAttribute("rankName");
					System.out.println("파일업로드");
					System.out.println(parent);
					//회사 폴더 생성
					String filename = companycode;
					File uploadPathFoler = new File(uploadPath, filename);
					
					if (uploadPathFoler.exists() == false) {
						uploadPathFoler.mkdirs();
						System.out.println("폴더생성");
					}
					List<CFileVO> returnfile=null;
					try {
					for(MultipartFile file:multipartFile) {
						
						String originalFileName = file.getOriginalFilename();
						System.out.println(originalFileName);
						System.out.println("파일부르기");
						
						UUID uuid = UUID.randomUUID();
						
						File saveFile = new File(uploadPath+"\\"+filename ,uuid+ "_" + file.getOriginalFilename());
						file.transferTo(saveFile);	
						
						//테이블 등록
						int forder= databaseservice.fileupload(companycode, parent, uploader, file.getOriginalFilename(), file.getSize(),uuid+ "_" + file.getOriginalFilename(),rankname);
						if(forder==1) {
							
							System.out.println("파일생성성공");
							List<CFileVO> refile=databaseservice.file(companycode);
							returnfile.add(refile.get(0));
						}
												
					}
					
					
					}catch(Exception e){
						System.out.println("파일부르기실패");
						e.printStackTrace();
					}
					
					
					
					
					Map<String, Object> result = new HashMap<String, Object>();
			        
					result.put("returnfile", returnfile);
					return result;
			    
										
					}
				
}
