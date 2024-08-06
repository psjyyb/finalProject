package com.arion.app.group.board.mapper;

import java.util.List;

import com.arion.app.group.board.service.BoardVO;

public interface BoardMapper {

	// 전체조회
	public List<BoardVO> selectBoardAll();
		
	public int getCount(BoardVO boardVO);
}
