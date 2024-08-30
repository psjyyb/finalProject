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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김철규
 * 작성일자 : 2024-08-21
 * 그룹웨어 게시판 : 게시글조회, 게시글상세보기, 글등록, 글삭제, 글수정
 */

@Controller
@RequiredArgsConstructor
public class BoardController {

	@Autowired
	FileService fsvc;

	private final BoardService boardService;
	private final ReplyService replyService;

	// noticeboard
	// 공지사항 전체조회
	@GetMapping("/group/noticeboardList") // 주소를 어디로 할건지 정해야됨
	public String noticeboardList(Model model, Criteria cri, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		cri.setCompanyCode(companyCode);
		cri.setBoardCategory("Y01");
		
		List<BoardVO> list = boardService.noticeboardList(cri);
		model.addAttribute("boards", list);
		model.addAttribute("page", new PageDTO(10, boardService.getTotal(cri), cri));
		
		return "group/board/noticeboardList"; // RETURN은 html 경로로 꼭 맞춰줘야됨
	}

	// 공지사항 게시글 상세조회
	@GetMapping("/group/noticeboardInfo")
	public String noticeboardInfo(BoardVO boardVO, Model model, HttpSession session) {
		// 파라미터 정의
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);

		// 게시글
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);
		// 첨부파일
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);

		// 댓글
		List<ReplyVO> list = replyService.replyList(boardVO.getBoardNo());
		model.addAttribute("comments", list);

		// 조회수 증가
		boardService.ViewCnt(boardVO.getBoardNo());
		return "group/board/noticeboardInfo";
	}

	// 공지사항 게시글 등록 (페이지)
	@GetMapping("/group/noticeboardInsert")
	public String noticeboardInsertForm() {
		
		return "group/board/noticeboardInsert";
	}

	// 공지사항 게시글 등록 (처리)
	@PostMapping("/noticeboardInsert")
	public String noticeboardInsertProcess(BoardVO boardVO, @RequestPart MultipartFile[] files, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo"); // HttpSession
		boardVO.setEmployeeNo(employeeNo); // 로그인세션 받아와서 작성자로
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode); // 회사코드를 받아서 그룹웨어를 사용하기 때문에 회사코드를 받아야됨
		boardVO.setBoardCategory("Y01"); // 글 등록하기위해선 어떤게시판에 등록할것인지 정해야함 (자유게시판이라 Y02)
		
		long bno = boardService.noticeinsertBoard(boardVO, files, companyCode);
		return "redirect:group/noticeboardInfo?boardNo=" + bno;
	}

	// 공지사항 게시글 수정 (페이지)
	@GetMapping("/group/noticeboardUpdate")
	public String noticeUpdateForm(BoardVO boardVO, Model model, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);
		
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);
		
		return "group/board/noticeboardUpdate";
	}

	// 공지사항 게시글 수정 (처리)
	@PostMapping("/group/noticeboardUpdate")
	@ResponseBody
	public Map<String, Object> noticeUpdateProcess(BoardVO boardVO, 
			 @RequestParam("files") MultipartFile[] files, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		return boardService.updateBoard(boardVO, files, companyCode);
	}

	// 공지사항 게시글 삭제 (처리)
	@GetMapping("/group/noticeboardDelete")
	public String noticeboardDelete(@RequestParam Integer boardNo) {
		boardService.noticedeleteBoard(boardNo);
		
		return "redirect:noticeboardList";
	}

	
	// freeboard
	// 자유게시판 전체조회
	@GetMapping("/group/freeboardList") // 주소를 어디로 할건지 정해야됨
	public String boardList(Model model, Criteria cri, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		cri.setCompanyCode(companyCode);
		cri.setBoardCategory("Y02");
		
		List<BoardVO> list = boardService.boardList(cri);
		model.addAttribute("boards", list);
		model.addAttribute("page", new PageDTO(10, boardService.getTotal(cri), cri));
		
		return "group/board/freeboardList"; // RETURN은 html 경로로 꼭 맞춰줘야됨
	}

	// 자유게시판 게시글 상세조회
	@GetMapping("/group/freeboardInfo")
	public String boardInfo(BoardVO boardVO, Model model, HttpSession session) {
		// 파라미터 정의
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);

		// 게시글
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);

		// 첨부파일
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);

		// 댓글
		List<ReplyVO> list = replyService.replyList(boardVO.getBoardNo());
		model.addAttribute("comments", list);

		// 조회수 증가
		boardService.ViewCnt(boardVO.getBoardNo());
		return "group/board/freeboardInfo";
	}

	// 자유게시판 게시글 등록 (페이지)
	@GetMapping("/group/freeboardInsert")
	public String boardInsertForm() {
		
		return "group/board/freeboardInsert";
	}

	// 자유게시판 게시글 등록 (처리)
	@PostMapping("/freeboardInsert")
	public String boardInsertProcess(BoardVO boardVO, @RequestPart MultipartFile[] files, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo"); // HttpSession
		boardVO.setEmployeeNo(employeeNo); // 로그인세션 받아와서 작성자로
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode); // 회사코드를 받아서 그룹웨어를 사용하기 때문에 회사코드를 받아야됨
		boardVO.setBoardCategory("Y02"); // 글 등록하기위해선 어떤게시판에 등록할것인지 정해야함 (자유게시판이라 Y02)
		
		long bno = boardService.insertBoard(boardVO, files, companyCode);
		return "redirect:group/freeboardInfo?boardNo=" + bno;
	}

	// 자유게시판 게시글 수정 (페이지)
	@GetMapping("/group/freeboardUpdate")
	public String boardUpdateForm(BoardVO boardVO, Model model, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);
		
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);
		
		return "group/board/freeboardUpdate";
	}

	// 자유게시판 게시글 수정 (처리)
	@PostMapping("/group/freeboardUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdateProcess(BoardVO boardVO, 
			 @RequestParam("files") MultipartFile[] files, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		return boardService.updateBoard(boardVO, files, companyCode);
	}

	// 자유게시판 게시글 삭제 (처리)
	@GetMapping("/group/freeboardDelete")
	public String boardDelete(@RequestParam Integer boardNo) {
		boardService.deleteBoard(boardNo);
		
		return "redirect:freeboardList";
	}

	
	// deptboard
	// 부서게시판 전체조회
	@GetMapping("/group/deptboardList") // 주소를 어디로 할건지 정해야됨
	public String deptboardList(Model model, Criteria cri, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		cri.setCompanyCode(companyCode);
		
		String department = (String) session.getAttribute("department");
		cri.setDepartment(department);
		cri.setBoardCategory("Y03");
		
		List<BoardVO> list = boardService.deptboardList(cri);
		model.addAttribute("boards", list);
		model.addAttribute("page", new PageDTO(10, boardService.getTotal(cri), cri));
		
		return "group/board/deptboardList"; // RETURN은 html 경로로 꼭 맞춰줘야됨
	}

	// 부서게시판 게시글 상세조회
	@GetMapping("/group/deptboardInfo")
	public String deptboardInfo(BoardVO boardVO, Model model, HttpSession session) {
		// 파라미터 정의
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);

		// 게시글
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);

		// 첨부파일
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);

		// 댓글
		List<ReplyVO> list = replyService.replyList(boardVO.getBoardNo());
		model.addAttribute("comments", list);

		// 조회수 증가
		boardService.ViewCnt(boardVO.getBoardNo());
		return "group/board/deptboardInfo";
	}

	// 부서게시판 게시글 등록 (페이지)
	@GetMapping("/group/deptboardInsert")
	public String deptboardInsertForm() {
		return "group/board/deptboardInsert";
	}

	// 부서게시판 게시글 등록 (처리)
	@PostMapping("/deptboardInsert")
	public String deptboardInsertProcess(BoardVO boardVO, @RequestPart MultipartFile[] files, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo"); // HttpSession
		boardVO.setEmployeeNo(employeeNo); // 로그인세션 받아와서 작성자로
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode); // 회사코드를 받아서 그룹웨어를 사용하기 때문에 회사코드를 받아야됨
		
		String department = (String) session.getAttribute("department");
		boardVO.setDepartment(department);
		boardVO.setBoardCategory("Y03"); // 글 등록하기위해선 어떤게시판에 등록할것인지 정해야함 (자유게시판이라 Y02)
		
		long bno = boardService.deptinsertBoard(boardVO, files, companyCode);
		return "redirect:group/deptboardInfo?boardNo=" + bno;
	}

	// 부서게시판 게시글 수정 (페이지)
	@GetMapping("/group/deptboardUpdate")
	public String deptUpdateForm(BoardVO boardVO, Model model, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		List<FileVO> fileVOList = fsvc.selectFiles("board", boardVO.getBoardNo(), companyCode);
		model.addAttribute("fileInfo", fileVOList);
		
		BoardVO findVO = boardService.boardInfo(boardVO);
		model.addAttribute("board", findVO);
		
		return "group/board/deptboardUpdate";
	}

	// 부서게시판 게시글 수정 (처리)
	@PostMapping("/group/deptboardUpdate")
	@ResponseBody
	public Map<String, Object> deptUpdateProcess(BoardVO boardVO, 
			 @RequestParam("files") MultipartFile[] files, HttpSession session) {
		
		String companyCode = (String) session.getAttribute("companyCode");
		boardVO.setCompanyCode(companyCode);
		
		return boardService.updateBoard(boardVO, files, companyCode);
	}

	// 부서게시판 게시글 삭제 (처리)
	@GetMapping("/group/deptboardDelete")
	public String deptboardDelete(@RequestParam Integer boardNo) {
		boardService.deptdeleteBoard(boardNo);
		
		return "redirect:deptboardList";
	}
}
