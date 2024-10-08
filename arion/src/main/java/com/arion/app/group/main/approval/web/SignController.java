package com.arion.app.group.main.approval.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.main.approval.service.SignService;

@Controller
public class SignController {
	
	@Value("${file.upload.url}")
	private String uploadPath;
		
	@Autowired
	SignService ssvc;
	
	@GetMapping("/group/doc/sign")
	public String empSignList(Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		String employeeId = (String) session.getAttribute("loginId");
		String empSign = ssvc.empSign(companyCode, employeeId);
		model.addAttribute("empSign" , empSign);
		return "group/document/sign/signList";
	}
	
	@PostMapping("/group/insertSign")
	public String insertSign(EmployeeVO empVO, @RequestParam("signatureData") String signatureData, HttpSession session) {
		String fileName = null;
		String companyCode = (String) session.getAttribute("companyCode");
		String employeeId = (String) session.getAttribute("loginId");
		try {
	        // 이미지 데이터에서 Base64 접두사 제거
	        String base64Image = signatureData.split(",")[1];
	        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

	        // 저장할 디렉토리 경로
	        String directoryPath = uploadPath + "/signatures/";
	        File directory = new File(directoryPath);

	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        UUID uuid = UUID.randomUUID();
	        fileName = uuid + "_" + employeeId  + ".png";
	        String filePath = directoryPath + fileName;

	        try (OutputStream stream = new FileOutputStream(filePath)) {
	            stream.write(imageBytes);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		empVO.setSignImg(fileName);
		empVO.setCompanyCode(companyCode);
		empVO.setEmployeeId(employeeId);
		
		ssvc.insertSign(empVO);
		
		return "redirect:/group/doc/sign";
		
	}
}
