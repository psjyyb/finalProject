package com.arion.app.group.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arion.app.group.board.mapper.ReplyMapper;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
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
	public int insertReply(ReplyVO replyVO) {
		int result = replyMapper.insertReply(replyVO);
		return result; 
	}

	// 댓글 수정
	@Override
	public Map<String, Object> updateReply(ReplyVO replyVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = replyMapper.updateReply(replyVO);
		if(result == 1 ) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", replyVO);
		return map;
	}
	
	// 댓글 삭제
	 @Override
	 public int deleteReply(int commentNo, int boardNo) {
	 	Map<String, Object> params = new HashMap<>();
	 	params.put("commentNo", commentNo);
	 	params.put("boardNo", boardNo);
	        
	 	return replyMapper.deleteReply(params);
	 }




}
