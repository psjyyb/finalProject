package com.arion.app.home.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeBoardController {
	
	@GetMapping("/home/faq")
	public String faq() {
		return "home/board/faq";
	}
	
	@GetMapping("/home/qna")
	public String qna() {
		return "home/board/qna";
	}
}
