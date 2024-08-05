package com.arion.app.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@GetMapping("/admin")
	public String admin() {
		return "admin/adminMain";
	}
	@GetMapping("/adminSubList")
	public String adminsubList() {
		return "admin/adminSubList";
	}
}
