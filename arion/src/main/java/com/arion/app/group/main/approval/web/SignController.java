package com.arion.app.group.main.approval.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.main.approval.service.SignService;

@Controller
public class SignController {
	
	@Autowired
	SignService ssvc;
	
	@GetMapping("/group/main/doc/sign")
	public String empSignList(Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		String employeeId = (String) session.getAttribute("employeeId");
		String empSign = ssvc.empSign(companyCode, employeeId);
		
		model.addAttribute(empSign);
		return "group/document/sign/signList";
	}
	
	@PostMapping("/insertSign")
	public Map<String, Object> insertSign(@RequestPart MultipartFile image) {
		
		
		return null;
	}
}
