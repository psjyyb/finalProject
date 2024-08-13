package com.arion.app.group.board.service;

import java.util.List;

public interface ReplyService {
	
	// 댓글 리스트
 	public List<ReplyVO> replyList(int boardNo);

 	// 댓글 등록
 	public int Replyinsert(ReplyVO replyVO);
 	
}
