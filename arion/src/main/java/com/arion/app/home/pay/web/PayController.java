package com.arion.app.home.pay.web;

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
	public String payView(PayVO payVO,Model model) {
		System.out.println(payVO);
		return "pay/payView";
	}
}
