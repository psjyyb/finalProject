package com.arion.app.group.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupEmployeeController {
	@GetMapping("/group")
	public String group() {
		return "group/main/groupMain";
	}
}
