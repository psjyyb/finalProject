package com.arion.app.home.pay.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.home.pay.service.PayVO;

@Controller
public class PayController {
		
	/*
	 * private PayService payService;
	 * 
	 * public PayController(PayService payService) { this.payService = payService; }
	 */
	@GetMapping("/home/payView")
	public String payView(PayVO payVO,Model model,HttpSession session) {
		String comCode = (String)session.getAttribute("companyCode");
		model.addAttribute("payInfo",payVO);
		return "pay/payView";
	}
}
