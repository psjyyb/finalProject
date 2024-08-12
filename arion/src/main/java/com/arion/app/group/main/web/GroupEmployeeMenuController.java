package com.arion.app.group.main.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.arion.app.group.main.service.MainService;
import com.arion.app.group.main.service.getModuleVO;

@ControllerAdvice
public class GroupEmployeeMenuController {

	@Autowired
	private MainService mainService;

	@Autowired
	private HttpSession httpSession;

	@ModelAttribute("modules")
	@Cacheable(value = "modules", key = "#session.getAttribute('companyCode') + '-' + #session.getAttribute('loginId')")
	public List<getModuleVO> populateModules() {
		String companyCode = (String) httpSession.getAttribute("companyCode");
		String employeeId = (String) httpSession.getAttribute("loginId");

		getModuleVO moduleVO = new getModuleVO();
		moduleVO.setCompanyCode(companyCode);
		moduleVO.setEmployeeId(employeeId);

		return mainService.getmoduleList(moduleVO);
	}
	
	 @GetMapping("/topbar")
	    public String topbar(HttpSession session, Model model) {
	        model.addAttribute("empName", session.getAttribute("empName"));
	        model.addAttribute("rankName", session.getAttribute("rankName"));
	        return "topbar"; 
	    }
	}
	

