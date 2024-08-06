package com.arion.app.home.main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arion.app.security.service.CompanyService;
import com.arion.app.security.service.CompanyVO;
import com.arion.app.security.service.LoginUserVO;

@Controller
public class HomeController {
	
	@Autowired
	CompanyService csvc;
	
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
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
    
//    @PostMapping("/signUpForm")
//    public String signUpProcess(CompanyVO companyVO) {
//    	csvc.insertCompany(companyVO);
//    	return "redirect:/login";
//    }
    
    @PostMapping("/signUpForm")
    public String signUpProcess(CompanyVO companyVO, Model model) {
        String companyCode = csvc.insertCompany(companyVO);
        model.addAttribute("companyCode", companyCode);
        return "home/signup/success";
    }
}