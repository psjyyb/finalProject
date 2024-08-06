package com.arion.app.group.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;

@Controller
public class BoardController {

	private BoardService boardService;
	
	// DI
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 전체조회 
	@GetMapping("/group/freeboardList")	// 주소를 어디로 할건지 정해야됨
	public String boardList(Model model) {
		List<BoardVO> list = boardService.boardList();
		model.addAttribute("boards", list);
		return "board/freeboardList";	// RETURN은 html 경로로 꼭 맞춰줘야됨
	}
}
