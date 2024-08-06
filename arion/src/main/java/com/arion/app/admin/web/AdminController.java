package com.arion.app.admin.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;

@Controller
public class AdminController {
	
	private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin/adminMain";
	}
	@GetMapping("/adminSubList")
	public String adminsubList(Model model) {
		List<AdminVO> subList = adminService.subListSelect();
		model.addAttribute("subList",subList);
		return "admin/adminSubList";
	}
	@GetMapping("/adminSubInfo")
	public String adminSunInfo(AdminVO adminVO, Model model) {
		AdminVO avo = adminService.subInfoSelect(adminVO);
		model.addAttribute("suncon",avo);
		return "admin/adminSubInfo";
	}
	@GetMapping("/adminModList")
	public String adminModList(Model model) {
		List<ModuleVO> mvo = adminService.modListSelect();
		model.addAttribute("moduleList",mvo);
		return "admin/adminModList";
	}
}
