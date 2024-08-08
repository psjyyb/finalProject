package com.arion.app.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;
import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;

@Controller
public class AdminController {

	private AdminService adminService;
	private FileService fileService;
	@Autowired
	public AdminController(AdminService adminService,FileService fileService) {
		this.adminService = adminService;
		this.fileService = fileService;
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
		System.out.println(moduleVO+"뭐하냐 발냄새나게 생겨서");
		int result = adminService.moduleInsert(moduleVO);
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
		List<FileVO> fileList = fileService.selectFiles("qna", qnaVO.getQnaNo());
		model.addAttribute("fileList",fileList);
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
