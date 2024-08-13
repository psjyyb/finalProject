package com.arion.app.group.main.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<getModuleVO> populateModules() {
        List<getModuleVO> modules = (List<getModuleVO>) httpSession.getAttribute("modules");

        if (modules == null) {
            // 세션에 modules가 없으면 새로 로드
            String companyCode = (String) httpSession.getAttribute("companyCode");
            String employeeId = (String) httpSession.getAttribute("loginId");

            getModuleVO moduleVO = new getModuleVO();
            moduleVO.setCompanyCode(companyCode);
            moduleVO.setEmployeeId(employeeId);

            modules = mainService.getmoduleList(moduleVO);
            httpSession.setAttribute("modules", modules);
        }

        return modules;
    }
	
	 @GetMapping("/topbar")
	    public String topbar(HttpSession session, Model model) {
	        model.addAttribute("empName", session.getAttribute("empName"));
	        model.addAttribute("rankName", session.getAttribute("rankName"));
	        return "topbar"; 
	    }
	}
	

