package com.arion.app.group.admin.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.GroupAdminVO;
import com.arion.app.group.admin.service.RankVO;
import com.arion.app.security.service.CompanyVO;

@Controller
public class GroupAdminController {

	private GroupAdminService gaService;
	private AdminService adminService;
	
	@Autowired
	GroupAdminController(GroupAdminService groupAdminService,AdminService adminService) {
		this.gaService = groupAdminService;
		this.adminService = adminService;
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
		GroupAdminVO userCnt = gaService.userCntSelect(comCode);
		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
		System.out.println(userCnt.getUsersCnt());
		model.addAttribute("userInfo",userCnt);
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
	
	@GetMapping("/groupAdmin/GAEndSubList")
	public String GAEndSubList(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<GroupAdminVO> list = gaService.endSubSelect(comCode);
		model.addAttribute("endsubList",list);
		return "groupAdmin/GAEndSubList";
	}
	@GetMapping("/groupAdmin/GASubInfo")
	public String adminSubInfo(AdminVO adminVO, Model model) {
		AdminVO avo = adminService.subInfoSelect(adminVO);
		model.addAttribute("suncon", avo);
		return "groupAdmin/GASubInfo";
	}
	@GetMapping("/groupAdmin/GANowContract")
	public String GANowContract(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		GroupAdminVO gvo = gaService.sunInfoSelect(comCode);
		model.addAttribute("subInfo",gvo);
		return "groupAdmin/GANowContract";
	}
	@GetMapping("/groupAdmin/GADeptList")
	public String GADeptList(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<DepartmentVO> deptList = gaService.deptListSelect(comCode);
		model.addAttribute("deptList",deptList);
		return "groupAdmin/GADeptList";
	}
	@PostMapping("/groupAdmin/GADeptSave")
	public String GADeptSave (DepartmentVO deptVO,HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		String depts = deptVO.getDepartmentName();
		String[] deptArr = depts.split(","); // 콤마를 기준으로 배열로 담아준다
		List<String> list = Arrays.asList(deptArr); // 배열에 담긴 값을 리스트로 
		gaService.saveDept(list,comCode);		
		return "redirect:/groupAdmin/GADeptList";
	}
	@GetMapping("/groupAdmin/GARankList")
	public String GARankList(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<RankVO> rankList = gaService.rankListSelect(comCode);
		model.addAttribute("rankList", rankList);
		return "groupAdmin/GARankList";
	}
	@PostMapping("/groupAdmin/GARankSave")
	public String GARankSave (RankVO rankVO,HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		gaService.saveRank(rankVO, comCode);
		return "redirect:/groupAdmin/GARankList";
	}
	@GetMapping("/groupAdmin/GAConCan")
	public String GAConCan() {
		return"/groupAdmin/GAConCan";
	}
	@GetMapping("/groupAdmin/GAComMod")
	public String GAComMod(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		CompanyVO cvo = gaService.comSelect(comCode);
		model.addAttribute("comInfo",cvo);
		return "/groupAdmin/GAComMod";
	}
	@PostMapping("/groupAdmin/GAComSave")
	public String GAcomSave(CompanyVO companyVO) {
		int result = gaService.saveCompany(companyVO);
		String url = null;
		if (result > -1) {
			url = "redirect:/groupAdmin/GAEmpList";
		} else {
			url = "redirect:/groupAdmin/GAComMod";
		}
		return url;
	}
	@GetMapping("/groupAdmin/checkPw")
	public String checkPw() {
		return"/groupAdmin/checkPw";
	}
	@PostMapping("/groupAdmin/checkPw")
	@ResponseBody
	public Map<String, Object> checkPwPro(@RequestBody String pw, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		return gaService.companyPw(comCode,pw);
	}
	@PostMapping("/groupAdmin/contractCancle")
	public String contractCancle() {
		return"";
	}
}
