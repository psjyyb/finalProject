package com.arion.app.group.board.mapper;

import java.util.List;
import java.util.Map;

import com.arion.app.group.board.service.ReplyVO;

public interface ReplyMapper {

	// 댓글리스트
	public List<ReplyVO> selectReplyAll(int boardNo);
	
	// 댓글 등록
	public int insertReply(ReplyVO replyVO);
	
	// 답글 등록
	public int insertReplyReply(ReplyVO replyVO);
	
	// 댓글 수정
	public int updateReply(ReplyVO replyVO);

	// 댓글 삭제
	int deleteReply(Map<String, Object> params);
	
	
}
