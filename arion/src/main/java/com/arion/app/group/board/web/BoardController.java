package com.arion.app.group.board.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// 게시글 상세조회
	@GetMapping("/group/freeboardInfo")
	public String boardInfo(BoardVO boardVO, Model model) {
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);
		return "board/freeboardInfo";
	}
	
	// 게시글 등록 (페이지)
	@GetMapping("/group/freeboardInsert")
	public String boardInsertForm() {
		return "board/freeboardInsert";
	}
	
	// 게시글 등록 (처리)
	@PostMapping("boardInsert")
	public String boardInsertProcess(BoardVO boardVO, HttpSession session) { 
		Long employeeNo = (Long) session.getAttribute("loginId"); //HttpSession
		boardVO.setEmployeeNo(employeeNo); //로그인세션 받아와서 작성자로
		long bno = boardService.insertBoard(boardVO);
		return "redirect:boardInfo?boardNo=" + bno;
	}
	
	// 게시글 수정 (페이지)
	@GetMapping("/group/freeboardUpdate")
	public String boardUpdateForm(BoardVO boardVo, Model model) {
		BoardVO findVO = boardService.boardInfo(boardVo);
		model.addAttribute("board", findVO);
		return "board/boardUpdate";
	}
	
	// 게시글 수정 (처리)
	@PostMapping("boardUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdateProcess(
							@RequestBody BoardVO boardVO) {
		return boardService.updateBoard(boardVO);
	}
	
	// 게시글 삭제
	@GetMapping("/group/freeboardDelete")
	public String boardDelete(@RequestParam Integer boardNo) {
		boardService.deleteBoard(boardNo);
		return "redirect:freeboardList";
	}
	
	
}
