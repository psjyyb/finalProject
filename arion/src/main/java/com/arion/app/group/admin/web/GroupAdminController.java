package com.arion.app.group.admin.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.RankVO;

@Controller
public class GroupAdminController {
	
	private GroupAdminService gaService;
	
	GroupAdminController(GroupAdminService groupAdminService){
		this.gaService = groupAdminService;
	}
	
	@GetMapping("/groupAdmin")
	public String groupAdmin() {
		return "groupAdmin/groupAdminMain";
	}
	
	@GetMapping("/groupAdmin/GAEmpList")
	public String GAEmpList(String companyCode,Model model,HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		List<EmployeeVO> list = gaService.empListSelect(comCode);
		model.addAttribute("empList",list);
		return "groupAdmin/GAEmpList";
	}
	@GetMapping("/groupAdmin/GAEmpInsert")
	public String GAEmpInsert(Model model,HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		List<DepartmentVO> deptList = gaService.deptListSelect(comCode);
		List<RankVO> rankList = gaService.rankListSelect(comCode);
		model.addAttribute("deptList",deptList);
		model.addAttribute("rankList",rankList);
		return "groupAdmin/GAEmpInsert";
	}
	@PostMapping("/groupAdmin/GAEmpInsert")
	public String GAEmpInsertPro(EmployeeVO empVO,HttpSession session) {
		int result = gaService.empInsert(empVO);
		return "redirect:GAEmpList";
	}
	@GetMapping("/groupAdmin/GAEmpUpdate")
	public String GAEmpUpdate(EmployeeVO employeeVO, Model model,HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		return "groupAdmin/GAEmpUpdate";
	}
	@GetMapping("/groupAdmin/GARank")
	public String GAEmpRank() {
		return "groupAdmin/GARank";
	}	
}
