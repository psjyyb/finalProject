package com.arion.app.admin.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;

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
		model.addAttribute("subList", subList);
		return "admin/adminSubList";
	}

	@GetMapping("/adminSubInfo")
	public String adminSunInfo(AdminVO adminVO, Model model) {
		AdminVO avo = adminService.subInfoSelect(adminVO);
		System.out.println(avo);
		model.addAttribute("suncon", avo);
		return "admin/adminSubInfo";
	}

	@GetMapping("/adminModList")
	public String adminModList(Model model) {
		List<ModuleVO> mvo = adminService.modListSelect();
		model.addAttribute("moduleList", mvo);
		return "admin/adminModList";
	}

	@GetMapping("/adminModInsert")
	public String adminModInsert(Model model) {
		return "admin/adminModInsert";
	}

	@PostMapping("/adminModInsert")
	public String adminModInsertPro(@ModelAttribute ModuleVO moduleVO) {
		int result = adminService.moduleInsert(moduleVO);
		System.out.println("결과~~~~~~~~~"+result);
		String url = null;
				if(result > 0) {
					url = "redirect:/adminModList";
				}else {
					url="redirect:/adminModInsert";
				}
		return url;
	}

	@GetMapping("/adminEndSubList")
	public String adminEndSubList(Model model) {
		List<AdminVO> list = adminService.endSunListSelect();
		model.addAttribute("endSubList", list);
		return "admin/adminEndSubList";
	}

	@GetMapping("/adminQnAList")
	public String adminQnAList(Model model) {
		List<QnAVO> list = adminService.qnaListSelect();
		model.addAttribute("qnaList", list);
		return "admin/adminQnAList";
	}

	@GetMapping("/adminQnAInfo")
	public String adminQnAInfo(QnAVO qnaVO, Model model) {
		QnAVO qvo = adminService.qnaInfoSelect(qnaVO);
		model.addAttribute("qna", qvo);
		return "admin/adminQnAInfo";
	}

	@PostMapping("/adminQnAInfo")
	public String adminQnAReply(QnAVO qnaVO) {
		System.out.println(qnaVO);
		int result = adminService.qnaReply(qnaVO);
		String url = null;
		if (result > 0) {
			url = "redirect:adminQnAList";
		}
		return url;
	}
}
