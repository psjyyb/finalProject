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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;

/*
 * 작성자 : 김철규
 * 작성일자 : 2024-08-14
 * 그룹웨어 게시판 : 게시글조회, 게시글상세보기, 글등록, 글삭제, 글수정
 */

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
	public String boardList(Model model, Criteria cri) {
		List<BoardVO> list = boardService.boardList(cri);
		model.addAttribute("boards", list);
		model.addAttribute("page", new PageDTO(10, boardService.getTotal(cri), cri));
		return "group/board/freeboardList";	// RETURN은 html 경로로 꼭 맞춰줘야됨
	}
	
	// 게시글 상세조회
	@GetMapping("/group/freeboardInfo")
	public String boardInfo(BoardVO boardVO, Model model) {
		BoardVO findVO = boardService.boardInfo(boardVO);
		boardService.ViewCnt(boardVO.getBoardNo());
		model.addAttribute("board", findVO);
		return "group/board/freeboardInfo";
	}
	
	// 게시글 등록 (페이지)
	@GetMapping("/group/freeboardInsert")
	public String boardInsertForm() {
		return "group/board/freeboardInsert";
	}
	
	// 게시글 등록 (처리)
	@PostMapping("/freeboardInsert")
	public String boardInsertProcess(BoardVO boardVO, HttpSession session) { 
		int employeeNo = (int) session.getAttribute("employeeNo"); //HttpSession
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setEmployeeNo(employeeNo); //로그인세션 받아와서 작성자로
		boardVO.setBoardCategory("Y02"); //글 등록하기위해선 어떤게시판에 등록할것인지 정해야함
		boardVO.setCompanyCode(companyCode); //회사코드를 받아서 그룹웨어를 사용하기 때문에 회사코드를 받아야됨
		long bno = boardService.insertBoard(boardVO);
		return "redirect:/group/freeboardInfo?boardNo=" + bno;
	}
	
	// 게시글 수정 (페이지)
	@GetMapping("/group/freeboardUpdate")
	public String boardUpdateForm(BoardVO boardVo, Model model) {
		BoardVO findVO = boardService.boardInfo(boardVo);
		model.addAttribute("board", findVO);
		return "group/board/freeboardUpdate";
	}
	
	// 게시글 수정 (처리)
	@PostMapping("/group/freeboardUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdateProcess(@RequestBody BoardVO boardVO) {
		return boardService.updateBoard(boardVO);
	}
	
	// 게시글 삭제
	@GetMapping("/group/freeboardDelete")
	public String boardDelete(@RequestParam Integer boardNo) {
		boardService.deleteBoard(boardNo);
		return "redirect:freeboardList";
	}
	
	
	
	
}
