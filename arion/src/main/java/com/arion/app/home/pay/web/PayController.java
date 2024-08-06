package com.arion.app.home.pay.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.home.pay.service.PayService;

@Controller
public class PayController {
		
	/*
	 * private PayService payService;
	 * 
	 * public PayController(PayService payService) { this.payService = payService; }
	 */
	@GetMapping("/home/payView")
	public String payView(Model model) {
		return "pay/payView";
	}
}
