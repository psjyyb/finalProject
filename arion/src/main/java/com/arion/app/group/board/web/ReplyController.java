package com.arion.app.group.board.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Controller
public class ReplyController {


	@Autowired
    private ReplyService replyService;

	// 댓글 등록(처리)
	@PostMapping("/replyInsert")
	public String Replyinsert(ReplyVO replyVO, HttpSession session) {
		String empname = (String) session.getAttribute("empName");
		String companyCode = (String) session.getAttribute("companyCode");
		replyVO.setCommentWrite(empname);
		replyVO.setCompanyCode(companyCode);
		int bno = replyService.Replyinsert(replyVO);
		return "redirect:/group/freeboardInfo?boardNo=" + replyVO.getBoardNo();
	}	

	
}
