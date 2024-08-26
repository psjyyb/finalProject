package com.arion.app.home.main.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.security.service.CompanyService;
import com.arion.app.security.service.CompanyVO;
import com.arion.app.security.service.HomeModuleService;
import com.arion.app.security.service.HomeModuleVO;
import com.arion.app.security.service.LoginUserVO;


/*
 * 작성자 : 김도겸
 * 작성일자 : 2024-08-05 ~ 2424-08-11
 * 홈페이지 구성 : 로그인, 모듈, 회원가입, 아이디 찾기(메일 발송), 비밀번호찾기(메일 발송)
 */
@Controller
public class HomeController {
	
	@Autowired
	CompanyService csvc;
	
	@Autowired
	HomeModuleService msvc;
	
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @GetMapping("/home")
    public String home(Model model) {
    	List<HomeModuleVO> mvo = msvc.selectModule();
    	model.addAttribute("moduleList", mvo);
        return "home/home";
    }
    
    @GetMapping("/groupAdmin/choice")
    public String choice(Model model, HttpSession session) {
    	String companyCode = (String) session.getAttribute("companyCode");
    	String companyName = csvc.selectCompanyName(companyCode);
    	model.addAttribute("companyName", companyName);
        return "home/choice";
    }
    
    @GetMapping("/company")
    public String companyHome(Authentication authentication, Model model) {
        LoginUserVO userDetails = (LoginUserVO) authentication.getPrincipal();
        String companyCode = userDetails.getUserVO().getCompanyCode(); 
        model.addAttribute("companyCode", companyCode);
        return "group/admin/groupAdmin";
    }

    @GetMapping("/employee")
    public String employeeHome(Authentication authentication, Model model) {
        LoginUserVO userDetails = (LoginUserVO) authentication.getPrincipal();
        String companyCode = userDetails.getUserVO().getCompanyCode(); 
        model.addAttribute("companyCode", companyCode);
        return "group/main/groupMain";
    }
    
    @GetMapping("/home/signCheck")
    public String signCheck() {
        return "home/signup/signCheck";
    }
    
    @GetMapping("/home/signUp")
    public String signUp() {
        return "home/signup/signUp";
    }
    
    @GetMapping("/home/module")
    public String module(Model model) {
    	List<HomeModuleVO> mvo = msvc.selectModule();
    	
    	int firstModuleNo = mvo.get(0).getModuleNo();
    	System.out.println(firstModuleNo);
    	List<HomeModuleVO> fmvo = msvc.explanModule(firstModuleNo);
    	model.addAttribute("moduleList", mvo);
    	model.addAttribute("moduleInfo", fmvo);
        return "home/module/module";
    }

    
    @PostMapping("/home/module")
    @ResponseBody
    public List<HomeModuleVO> moduleInfo(@RequestParam int moduleNo) {
    	return msvc.explanModule(moduleNo);
    } 

    
    @GetMapping("/home/service")
    public String service(Model model) {
    	List<HomeModuleVO> mvo = msvc.selectModule();    	
    	model.addAttribute("moduleList", mvo);
        return "home/module/service";
    }
    
    
    @PostMapping("/home/signUpForm")
    public String signUpProcess(CompanyVO companyVO, Model model) {
        String companyCode = csvc.insertCompany(companyVO);
        model.addAttribute("companyCode", companyCode);
        return "home/signup/success";
    }
    
    @GetMapping("/home/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam String companyId) {
        return csvc.IdCheck(companyId);
    }
    
    @PostMapping("/home/findId")
    @ResponseBody
    public Map<String, String> findCompanyId(@RequestParam("ceoName") String ceoName, @RequestParam("companyBusinessNumber") long companyBusinessNumber, Model model) {
    	CompanyVO companyVO = csvc.findId(ceoName, companyBusinessNumber);
    	Map<String, String> response = new HashMap<>();
		if(companyVO != null) {
			response.put("message","회사코드와 아이디가 이메일로 전송되었습니다." );  				
		} else {
			response.put("message", "해당 정보가 없습니다.");
		}
    	return response; 	
    }
    
    @PostMapping("/home/resetPassword")
    @ResponseBody
    public Map<String, String> resetPassword(@RequestParam("companyCode") String companyCode, 
                                @RequestParam("companyId") String companyId, 
                                Model model) {
        int result = csvc.updatePw(companyCode, companyId);
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
        	response.put("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } else {
        	response.put("message", "비밀번호 재설정에 실패했습니다.");
        }
        return response; 
    }
}