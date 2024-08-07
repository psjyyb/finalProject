package com.arion.app.group.admin.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.RankVO;

@Controller
public class GroupAdminController {

	private GroupAdminService gaService;

	GroupAdminController(GroupAdminService groupAdminService) {
		this.gaService = groupAdminService;
	}

	@GetMapping("/groupAdmin")
	public String groupAdmin() {
		return "groupAdmin/groupAdminMain";
	}

	@GetMapping("/groupAdmin/GAEmpList")
	public String GAEmpList(String companyCode, Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<EmployeeVO> list = gaService.empListSelect(comCode);
		model.addAttribute("empList", list);
		return "groupAdmin/GAEmpList";
	}

	@GetMapping("/groupAdmin/GAEmpInsert")
	public String GAEmpInsert(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<DepartmentVO> deptList = gaService.deptListSelect(comCode);
		List<RankVO> rankList = gaService.rankListSelect(comCode);
		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
		return "groupAdmin/GAEmpInsert";
	}

	@PostMapping("/groupAdmin/GAEmpInsert")
	public String GAEmpInsertPro(EmployeeVO empVO, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		empVO.setCompanyCode(comCode);
		int result = gaService.empInsert(empVO);
		String url = null;
		if (result > -1) {
			url = "redirect:GAEmpUpdate?employeeNo=" + result;
		} else {
			url = "redirect:GAEmpList";
		}
		return url;
	}

	@GetMapping("/groupAdmin/GAEmpUpdate")
	public String GAEmpUpdate(@RequestParam Integer employeeNo, Model model) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployeeNo(employeeNo);
		EmployeeVO evo = gaService.empInfoSelect(employeeVO);
		System.out.println(evo);
		model.addAttribute("empInfo",evo);
		return "groupAdmin/GAEmpUpdate";
	}
	@PostMapping("/groupAdmin/GAEmpUpdate")
	public String GAEmpupdatePro(EmployeeVO employeeVO){
		int result = gaService.empUpdate(employeeVO);
		String url = null;
		if (result > -1) {
			url = "redirect:/groupAdmin/GAEmpList";
		} else {
			url = "redirect:GAEmpUpdate?employeeNo=" + result;
		}
		return url;
	}
	
	@GetMapping("/groupAdmin/GAEmpDelete")
	public String GAEmpDelete(EmployeeVO empVO) {
		gaService.empDelete(empVO);
		return "redirect:/groupAdmin/GAEmpList";
	}
	
	@GetMapping("/groupAdmin/GARank")
	public String GAEmpRank() {
		return "groupAdmin/GARank";
	}
}
