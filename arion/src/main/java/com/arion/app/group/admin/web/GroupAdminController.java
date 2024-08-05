package com.arion.app.group.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupAdminController {
	@GetMapping("/groupAdmin")
	public String groupAdmin() {
		return "groupAdmin/groupAdminMain";
	}
}
