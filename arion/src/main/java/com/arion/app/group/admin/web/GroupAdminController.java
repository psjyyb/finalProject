package com.arion.app.group.admin.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;

@Controller
public class GroupAdminController {
	
	private GroupAdminService gaServive;
	
	GroupAdminController(GroupAdminService groupAdminService){
		this.gaServive = groupAdminService;
	}
	
	@GetMapping("group/groupAdmin")
	public String groupAdmin() {
		return "groupAdmin/groupAdminMain";
	}
	
	@GetMapping("group/GAEmpList")
	public String GAEmpList(@RequestParam("companyCode") String companyCode,Model model) {
		List<EmployeeVO> list = gaServive.empListSelect(companyCode);
		model.addAttribute("empList",list);
		return "groupAdmin/GAEmpList";
	}

}
