package com.arion.app.group.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.board.mapper.BoardMapper;
import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;

@Service
public class BoardServiceImpl implements BoardService{

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	// 전체 게시글 조회
	@Override
	public List<BoardVO> boardList() {
		return boardMapper.selectBoardAll();
	}
	
}
