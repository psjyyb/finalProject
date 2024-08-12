package com.arion.app.group.board.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.board.mapper.ReplyMapper;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.board.service.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyMapper replyMapper;
	
	@Override
	public void insert(ReplyVO reply) {
		replyMapper.insert(reply);
	}

	@Override
	public void delete(int commentNo) {
		replyMapper.delete(commentNo);
	}
	


}
