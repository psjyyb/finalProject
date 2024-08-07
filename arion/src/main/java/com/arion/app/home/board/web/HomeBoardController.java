package com.arion.app.home.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.home.board.service.QnaService;

@Controller
public class HomeBoardController {
	
	@Autowired
	QnaService qsvc;
	
	@GetMapping("/home/faq")
	public String faq() {
		return "home/board/faq";
	}
	
	@GetMapping("/home/qna")
	public String qna(Model model) {
		List<HomeQnaVO> qvo = qsvc.selectQnAList();
		model.addAttribute("qnaList", qvo);		
		return "home/board/qna";
	}
	
}
