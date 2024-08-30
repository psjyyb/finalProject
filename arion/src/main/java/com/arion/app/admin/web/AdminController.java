package com.arion.app.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleFileVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;
import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
import com.arion.app.admin.service.ChartVO;

	/*
	 * 작성자 : 박성준
	 * 작성일자 : 2024-08-05
	 * 사이트 관리자 : 구독서비스 이용중인 회사목록조회, 계약서조회(종료된계약,진행중인계약),
	 *             모듈목록조회, 모듈상세조회, 모듈등록/수정/삭제, Q&A목록조회, Q&A상세보기/답글달기
	 * */
	


@Controller
public class AdminController {

	private AdminService adminService;
	private FileService fileService;

	@Autowired
	public AdminController(AdminService adminService, FileService fileService) {
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

	@GetMapping("/adminEndSubList")
	public String adminEndSubList(Model model) {
		List<AdminVO> list = adminService.endSunListSelect();
		model.addAttribute("endSubList", list);
		return "admin/adminEndSubList";
	}

	@GetMapping("/admin/adminQnAList")
	public String adminQnAList(Model model) {
		List<QnAVO> list = adminService.qnaListSelect();
		model.addAttribute("qnaList", list);
		return "admin/adminQnAList";
	}

	@GetMapping("/adminQnAInfo")
	public String adminQnAInfo(QnAVO qnaVO, Model model) {
		QnAVO qvo = adminService.qnaInfoSelect(qnaVO);
		String companyCode = null;
		List<FileVO> fileList = fileService.selectFiles("qna", qnaVO.getQnaNo(), companyCode);
		model.addAttribute("fileList", fileList);
		model.addAttribute("qna", qvo);
		return "admin/adminQnAInfo";
	}

	@PostMapping("/adminQnAInfo")
	public String adminQnAReply(QnAVO qnaVO) {
		int result = adminService.qnaReply(qnaVO);
		String url = null;
		if (result > 0) {
			url = "redirect:adminQnAList";
		}
		return url;
	}

	@GetMapping("/adminModInfo")
	public String adminModInfo(ModuleVO moduleVO, Model model) {
		ModuleVO mvo = adminService.modSelect(moduleVO);
		List<ModuleFileVO> list = adminService.modFileSelect(moduleVO);
		model.addAttribute("fileList", list);
		model.addAttribute("modInfo", mvo);
		return "admin/adminModInfo";
	}

	@PostMapping("/adminModInsert")
	public String adminModInsertPro(@ModelAttribute ModuleVO moduleVO) {
		int result = adminService.moduleInsert(moduleVO);
		String url = null;
		if (result > 0) {
			url = "redirect:/adminModList";
		} else {
			url = "redirect:/adminModInsert";
		}
		return url;
	}

	@PostMapping("/adminModUpdate")
	public String adminModUpdate(@ModelAttribute ModuleVO modVO) {
		int result = adminService.modUpdate(modVO);
		String url = null;
		if (result > 0) {
			url = "redirect:/adminModList";
		} else {
			url = "redirect:/adminModUpdate";
		}
		return url;
	}
	@GetMapping("/adminModDel")
	public String adminModDel(int moduleNo) {
		int result = adminService.modDelete(moduleNo);
		String url = null;
		if(result > 0) {
			url = "redirect:/adminModList";
		}else {
			url = "redirect:/adminModInfo";
		}
		return url;
	}
	@PostMapping("/adminAreaChart")
	@ResponseBody
	public List<ChartVO> areaChart(){
		List<ChartVO> list = adminService.areaChart();
		return list;
	}
	@PostMapping("/adminPieChart")
	@ResponseBody
	public List<ChartVO> pieChart(){
		return adminService.pieChart();
	}
	
	@GetMapping("/arion/error")
	public String error(){
		System.out.println("이거 찍히면 에러난거임");
		return "common/error/404";
	}
}
