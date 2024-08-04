package com.arion.app.home.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageBoard {

	@GetMapping("/home/board")
	public String board() {
		return "home/board/boardList";
	}
}
