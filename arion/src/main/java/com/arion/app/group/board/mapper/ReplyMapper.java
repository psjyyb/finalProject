package com.arion.app.group.board.mapper;

import java.util.List;

import com.arion.app.group.board.service.ReplyVO;

public interface ReplyMapper {

	// 댓글리스트
	public List<ReplyVO> selectReplyAll(int boardNo);
	
	// 댓글 등록
	public int Replyinsert(ReplyVO replyVO);
	
	
}
