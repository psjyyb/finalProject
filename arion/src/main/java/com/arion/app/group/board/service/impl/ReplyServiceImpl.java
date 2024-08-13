package com.arion.app.group.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arion.app.group.board.mapper.ReplyMapper;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	private ReplyMapper replyMapper;

	@Autowired
	public ReplyServiceImpl(ReplyMapper replymapper) {
		this.replyMapper = replymapper;
	}

	// 댓글 리스트
	@Override
	public List<ReplyVO> replyList(int boardNo) {
		return replyMapper.selectReplyAll(boardNo);
	}

	// 댓글 등록
	@Override
	public int Replyinsert(ReplyVO replyVO) {
		int result = replyMapper.Replyinsert(replyVO);
		return result == 1 ? replyVO.getCommentNo() : -1;
	}



}
