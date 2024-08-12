package com.arion.app.home.pay.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.security.service.CompanyVO;

@Controller
public class PayController {
		
	private PayService payService;
	
	@Autowired
	public PayController(PayService payService) {
		this.payService = payService;
	}
	@GetMapping("/home/payView")
	public String payView(PayVO payVO,Model model,HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		CompanyVO cvo = payService.selectCom(comCode);
		System.out.println(cvo);
		model.addAttribute("comInfo",cvo);
		model.addAttribute("payInfo",payVO);
		return "pay/payView";
	}
}
