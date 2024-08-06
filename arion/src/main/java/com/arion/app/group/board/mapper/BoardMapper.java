package com.arion.app.group.board.mapper;

import java.util.List;

import com.arion.app.group.board.service.BoardVO;

public interface BoardMapper {

	// 전체조회
	public List<BoardVO> selectBoardAll();
	
//	// 상세조회
//	public BoardVO selectBoardInfo(BoardVO boardVO);
//	
//	// 등록	 
//	public int insertBoardInfo(BoardVO boardVO);
//	
//	// 수정   
//	public int updateBoardInfo(BoardVO boardVO);
//	
//	// 삭제
//	public int deleteBoardInfo(int boardNO);
}
