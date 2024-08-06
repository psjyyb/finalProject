package com.arion.app.group.main.mail.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;

@Controller
public class MailController {
	
	@Autowired
	MailService mailService;
	//받은메일조회
	@GetMapping("/group/mail")
	public String mailList(Model model) {
	    List<MailVO> list = mailService.mailList();
	    model.addAttribute("mailList", list);
	    return "group/mail/Mymail";
	}
	

	//메일쓰기
	@GetMapping("/group/WriteMail")
	public String WriteMail() {
	    return "group/mail/WriteMail";
	}
	
	
}
