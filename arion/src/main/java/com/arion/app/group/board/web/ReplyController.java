package com.arion.app.group.board.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Controller
public class ReplyController {


	@Autowired
    private ReplyService replyService;

	// 댓글 등록(처리)
	@PostMapping("/group/insertReply")
	@ResponseBody
	public int Replyinsert(ReplyVO replyVO, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo");
		String companyCode = (String) session.getAttribute("companyCode");
		replyVO.setCommentWrite(Integer.toString(employeeNo)); //
		replyVO.setCompanyCode(companyCode);
		int bno = replyService.insertReply(replyVO);
		return bno;
	}
	
	// 댓글 수정
	@PostMapping("/group/updateReply")
	@ResponseBody
	public Map<String, Object> replyUpdateProcess(@RequestBody ReplyVO replyVO) {
		return replyService.updateReply(replyVO);
	}
	
	
	// 댓글 삭제
	@PostMapping("/group/deleteReply")
    public String deleteReply(@RequestParam("commentNo") int commentNo, @RequestParam("boardNo") int boardNo) {
        replyService.deleteReply(commentNo, boardNo);
        return "redirect:/group/freeboardInfo?boardNo=" + boardNo;
    }
}
