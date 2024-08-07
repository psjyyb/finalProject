package com.arion.app.group.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// 게시글 상세조회
	@Override
	public BoardVO boardInfo(BoardVO boardVO) {
		return boardMapper.selectBoardInfo(boardVO);
	}

	// 게시글 등록
	@Override
	public long insertBoard(BoardVO boardVO) {
		int result = boardMapper.insertBoardInfo(boardVO);
		
		return result == 1 ? boardVO.getBoardNo() : -1;
	}

	@Override
	public int deleteBoard(int boardNO) {
		return boardMapper.deleteBoardInfo(boardNO);
	}

	
	
	
}
