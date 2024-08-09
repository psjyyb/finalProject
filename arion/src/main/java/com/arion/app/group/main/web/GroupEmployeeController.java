package com.arion.app.group.main.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.group.main.service.MainService;
import com.arion.app.group.main.service.getModuleVO;

@Controller
public class GroupEmployeeController {

    @Autowired
    private MainService mainService;

    @Autowired
    private HttpSession httpSession;


    @GetMapping("/group")
    public String getmoduleList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("loginId");

        System.out.println(companyCode+"여기왔니?");

            getModuleVO moduleVO = new getModuleVO();
            moduleVO.setCompanyCode(companyCode);
            moduleVO.setEmployeeId(employeeId);

            List<getModuleVO> modules = mainService.getmoduleList(moduleVO);
            model.addAttribute("modules", modules);
            System.out.println("나이탕");
            model.addAttribute("mailPageUrl", "/group/mail/received");
            
        return "group/main/groupMain";
    }
    
}