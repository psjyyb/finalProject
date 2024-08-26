package com.arion.app.group.board.service;

import java.util.List;
import java.util.Map;

public interface ReplyService {
	
	// 댓글 리스트
 	public List<ReplyVO> replyList(int boardNo);

 	// 댓글 등록
 	public int insertReply(ReplyVO replyVO);
 	
 	// 답글 등록
 	// public int insertReplyReply(ReplyVO replyVO);
 	
 	// 댓글 수정
 	public Map<String, Object> updateReply(ReplyVO replyVO);

 	// 댓글 삭제
 	public int deleteReply(int commentNo, int boardNo);
 	
}
