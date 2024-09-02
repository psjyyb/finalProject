package com.arion.app.group.main.database.web;

import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.arion.app.group.main.database.service.FileinfoVO;
import com.arion.app.group.main.database.service.UnderRankVO;
import com.arion.app.home.board.service.HomeQnaVO;

@Controller
@RequestMapping("/group/database")
public class DatabaseController {

	@Value("${file.upload.url}")
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
					Integer startfordercheck =databaseservice.startfordercheck(companycode);
					HttpSession session = request.getSession();			
					
					String uploader = (String)session.getAttribute("empName");
					if(startfordercheck==null) {
						
						System.out.println("최상단 폴더생성");
						databaseservice.startfordercreate(companycode,uploader);						
					}
					
//					List<CFileVO> startforder= databaseservice.startforder(companycode);				
//					System.out.println(startforder);
//					result.put("startforder", startforder);
					Map<String, Object> result = new HashMap<String, Object>();
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
					
					
					List<CFileVO> returnfile = new ArrayList<>();
					
					
					try {
					for(MultipartFile file:multipartFile) {
						
						String originalFileName = file.getOriginalFilename();
						System.out.println(originalFileName);
						System.out.println("파일부르기");
						
						UUID uuid = UUID.randomUUID();
						
						File saveFile = new File(uploadPath+"\\"+filename ,uuid+ "_" + file.getOriginalFilename());
						file.transferTo(saveFile);	
						
						  
						
						//테이블 등록
						int forder= databaseservice.fileupload(companycode, parent, uploader, file.getOriginalFilename(),file.getSize(),uuid+ "_" + file.getOriginalFilename(),rankname);
						if(forder==1) {
							
							System.out.println("파일생성성공");
							CFileVO refile=databaseservice.file(companycode);
							returnfile.add(refile);
							System.out.println("생성확인"+refile.getMe());
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
				
				//ajax 데이터 정보 출력
				@RequestMapping("/datainfo")
				@ResponseBody
				public Map<String, Object> datainfo(HttpServletRequest request,
						@RequestParam(value = "selectdataid",required = false) int selectdataid
						) throws Exception {
					System.out.println("불러올데이터:"+selectdataid);
					
					HttpSession session = request.getSession();			
					String companycode = (String)session.getAttribute("companyCode");
					
					
					List<FileinfoVO> datainfo= databaseservice.fileinfo(companycode,selectdataid);
					
					System.out.println("불러올데이터양:"+datainfo.size());
					
					Map<String, Object> result = new HashMap<String, Object>();
					
					result.put("datainfo",datainfo);
					
					return result;
				}		
				
				//ajax 다운로드
				@RequestMapping("/download")
				@ResponseBody
				public void download(HttpServletRequest request,
						@RequestParam(value = "selectdataid",required = false) int selectdataid
						, HttpServletResponse response) throws IOException {
					System.out.println("다운로드데이터:"+selectdataid);
					
					HttpSession session = request.getSession();			
					String companycode = (String)session.getAttribute("companyCode");
					
					List<FileinfoVO> datainfo= databaseservice.fileinfo(companycode,selectdataid);
					
					System.out.println("다운로드데이터 이름:"+datainfo.get(0).getCname());
					//테스트
					String fname = datainfo.get(0).getFilename();

					File file = new File(uploadPath+"\\"+companycode+"\\"+datainfo.get(0).getCname());
					FileInputStream in = new FileInputStream(file);
					System.out.println(file.getName());
					fname = new String(fname.getBytes("UTF-8"), "8859_1");

			
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=" + fname);
					OutputStream os = response.getOutputStream();
			
					
					int length;
					byte[] b = new byte[(int) file.length()];
					while ((length = in.read(b)) > 0) {
						os.write(b, 0, length); // outputSteam의 write 메소드. 
					}

					os.flush();

					os.close();
					in.close();
				
					
					
				}	
				
				//ajax 파일 삭제
				@RequestMapping("/filedelete")
				@ResponseBody
				public Map<String, Object> filedelete(HttpServletRequest request,
						@RequestParam(value = "deleteid",required = false) int deleteid
						) throws Exception {
					System.out.println("삭제할데이터:"+deleteid);
					
					HttpSession session = request.getSession();			
					String companycode = (String)session.getAttribute("companyCode");
					
					
					int datadelete= databaseservice.filedelete(companycode,deleteid);
					
                    if(datadelete==1) {
						
						System.out.println("파일삭제성공");
						
					}
					
					Map<String, Object> result = new HashMap<String, Object>();
					
					result.put("datadelete",datadelete);
					
					return result;
				}
				
				//ajax 폴더 삭제
				@RequestMapping("/forderdelete")
				@ResponseBody
				public Map<String, Object> forderdelete(HttpServletRequest request,
						@RequestParam(value = "deleteforderid",required = false) int deleteforderid
						) throws Exception {
					System.out.println("삭제할폴더아이디:"+deleteforderid);
					
					HttpSession session = request.getSession();			
					String companycode = (String)session.getAttribute("companyCode");
					
					
					int datadelete= databaseservice.forderdelete(companycode,deleteforderid);
					
                    if(datadelete>0) {
						
						System.out.println("폴더삭제성공");
						
					}
					
					Map<String, Object> result = new HashMap<String, Object>();
					
					result.put("forderdelete",datadelete);
					
					return result;
				}
}
