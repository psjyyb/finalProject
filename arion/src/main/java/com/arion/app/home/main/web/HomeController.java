package com.arion.app.home.main.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.security.service.LoginUserVO;

@Controller
public class HomeController {
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
}