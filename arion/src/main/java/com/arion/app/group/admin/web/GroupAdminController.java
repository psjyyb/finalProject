package com.arion.app.group.admin.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.group.admin.service.DepartmentListVO;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.GroupAdminVO;
import com.arion.app.group.admin.service.PayDetailVO;
import com.arion.app.group.admin.service.PayListVO;
import com.arion.app.group.admin.service.RankVO;
import com.arion.app.security.service.CompanyVO;

	/*
	 * 작성자 : 박성준
	 * 작성일자 : 2024-08-05
	 * 그룹웨어 관리자 : 사원목록조회, 사원상세조회/수정/삭제, 사원등록, 부서목록조회/수정/삭제,
	 *              직급목록조회/수정/삭제, 회사정보수정, 지난계약목록/상세조회,
	 * 				 현재계약정보조회, 계약해지, 계약수정
	 * */


@Controller
public class GroupAdminController {

	private GroupAdminService gaService;
	private AdminService adminService;
	
	@Autowired
	GroupAdminController(GroupAdminService groupAdminService,AdminService adminService) {
		this.gaService = groupAdminService;
		this.adminService = adminService;
	}

	/*
	 * @GetMapping("/groupAdmin") public String groupAdmin() { return
	 * "groupAdmin/groupAdminMain"; }
	 */

	@GetMapping("/groupAdmin")
	public String GAEmpList(String companyCode, Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<EmployeeVO> list = gaService.empListSelect(comCode);
		model.addAttribute("empList", list);
		return "/groupAdmin/GAEmpList";
	}

	@GetMapping("/groupAdmin/GAEmpInsert")
	public String GAEmpInsert(Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		List<DepartmentVO> deptList = gaService.deptListSelect(comCode);
		List<RankVO> rankList = gaService.rankListSelect(comCode);
		GroupAdminVO userCnt = gaService.userCntSelect(comCode);
		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
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
			url = "redirect:/groupAdmin/GAEmpUpdate?employeeNo=" + result;
		} else {
			url = "redirect:/groupAdmin";
		}
		return url;
	}

	@GetMapping("/groupAdmin/GAEmpUpdate")
	public String GAEmpUpdate(@RequestParam Integer employeeNo, Model model,HttpSession session) {
		String comCode= (String)session.getAttribute("companyCode");
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployeeNo(employeeNo);
		EmployeeVO evo = gaService.empInfoSelect(employeeVO);
		List<DepartmentVO> deptList = gaService.deptListSelect(comCode);
		List<RankVO> rankList = gaService.rankListSelect(comCode);
		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
		model.addAttribute("empInfo",evo);
		return "groupAdmin/GAEmpUpdate";
	}
	@PostMapping("/groupAdmin/GAEmpUpdate")
	public String GAEmpupdatePro(EmployeeVO employeeVO){
		int result = gaService.empUpdate(employeeVO);
		String url = null;
		if (result > -1) {
			url = "redirect:/groupAdmin";
		} else {
			url = "redirect:GAEmpUpdate?employeeNo=" + result;
		}
		return url;
	}
	
	@GetMapping("/groupAdmin/GAEmpDelete")
	public String GAEmpDelete(EmployeeVO empVO) {
		gaService.empDelete(empVO);
		return "redirect:/groupAdmin";
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
		List<EmployeeVO> empList = gaService.empListSelect(comCode);
		model.addAttribute("empList",empList);
		model.addAttribute("deptList",deptList);
		return "groupAdmin/GADeptList";
	}
	@PostMapping("/groupAdmin/GADeptSave")
	public String GADeptSave(@ModelAttribute("departments") DepartmentListVO deptListVO, HttpSession session) {
	    String comCode = (String) session.getAttribute("companyCode");
	    gaService.saveDept(deptListVO, comCode);  // 서비스로 전체 부서 목록 전달
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
	public String GAConCan(Model model, HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		int result = gaService.contractNo(comCode);
		model.addAttribute("contractNo",result);
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
			url = "redirect:/groupAdmin";
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
	public String contractCancle(int contractNo, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		return gaService.cancleContract(contractNo,comCode);
	}
	@GetMapping("/groupAdmin/GAPayList")
	public String gaPayList(HttpSession session, Model model) {
		String comCode = (String)session.getAttribute("companyCode");
		List<PayListVO> list = gaService.payList(comCode);
		model.addAttribute("payList",list);
		return "/groupAdmin/GAPayList";
	}
	@GetMapping("/groupAdmin/GAPayInfo")
	public String gaPayInfo(int payNo,Model model) {
		PayListVO lvo = gaService.payInfo(payNo);
		List<PayDetailVO> list = gaService.payDetailInfo(payNo);
		model.addAttribute("detailList",list);
		model.addAttribute("payInfo",lvo);
		return "/groupAdmin/GAPayInfo";
	}
	@PostMapping("/groupAdmin/GANowContract")
	public Map<String,Object>extendContract(int period,HttpSession session){
		String comCode = (String)session.getAttribute("companyCode"); 
		return gaService.extendContract(period, comCode);
	}
	@PostMapping("/groupAdmin/checkId")
	@ResponseBody
	public boolean checkOverlapId(String employeeId,HttpSession session){
		String comCode = (String) session.getAttribute("companyCode");
	return gaService.checkOverlapId(comCode, employeeId);
	}
	@PostMapping("/groupAdmin/GAEmpExcelInsert")
	@ResponseBody
	public List<EmployeeVO> excelEmpInsert(@RequestParam("excelFile") MultipartFile excelFile ,Model model,HttpSession session) throws IOException{
		String companyCode = (String) session.getAttribute("companyCode");
		return gaService.excelEmpInsert(excelFile,companyCode);
	
	}
	@PostMapping("/groupAdmin/GAEmpResign")
	@ResponseBody
	public int empResign(EmployeeVO employeeVO) {
		return gaService.resignEmp(employeeVO);
	}
}
