package com.arion.app.group.main.mail.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {
	@GetMapping("/mail")
	public String group() {
		return "group/main/mail";
	}
}
