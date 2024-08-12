package com.arion.app.group.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Controller
@RequestMapping("/group")
public class CommentController {

	
	@Autowired
    private ReplyService replyService;

    // 댓글 추가
	@PostMapping("/commentInsert")
    public ResponseEntity<?> commentInsert(
            @RequestParam("boardNo") int boardNo,
            @RequestParam("commentContent") String commentContent,
            @RequestParam("commentWrite") String commentWrite) {

        ReplyVO reply = new ReplyVO();
        reply.setBoardNo(boardNo);
        reply.setCommentContent(commentContent);
        reply.setCommentWrite(commentWrite);
        reply.setCommentDate(new java.util.Date()); // 현재 날짜

        replyService.insert(reply);
        return ResponseEntity.ok("댓글이 성공적으로 추가되었습니다.");
		}

		//댓글 삭제
		@PostMapping("/commentDelete")
		public ResponseEntity<?> commentDelete(@RequestParam("commentNo") int commentNo) {
			try {
				replyService.delete(commentNo);
				return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
			} catch (Exception e) {
				// 예외 발생 시
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("댓글 삭제에 실패했습니다.");
			}
		}
}
