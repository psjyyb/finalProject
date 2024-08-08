package com.arion.app.group.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	// 자유게시판
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
		long result = boardMapper.insertBoardInfo(boardVO);
		return result == 1 ? boardVO.getBoardNo() : -1;
	}

	// 게시글 수정
	@Override
	public Map<String, Object> updateBoard(BoardVO boardVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = boardMapper.updateBoardInfo(boardVO);
		if(result == 1) {
			isSuccessed = true;
		}		
		map.put("result", isSuccessed);
		map.put("target", boardVO);
		
		return map;
	}
	
	// 게시글 삭제
	@Override
	public int deleteBoard(int boardNO) {
		return boardMapper.deleteBoardInfo(boardNO);
	}

	// 게시글 조회수
	@Override
	public int ViewCnt(int viewCnt) {
		return boardMapper.ViewCnt(viewCnt);
	}
	
	

	


	
}
