package com.arion.app.group.board.mapper;

import java.util.List;
import java.util.Map;

import com.arion.app.group.board.service.BoardVO;

public interface BoardMapper {

	// 전체조회
	public List<BoardVO> selectBoardAll();
	
	// 글 상세조회
	public BoardVO selectBoardInfo(BoardVO boardVO);

	// 글 등록
	public int insertBoardInfo(BoardVO boardVO);
	
	// 글 수정
	public int updateBoardInfo(BoardVO boardVO);
	
	// 글 삭제
	public int deleteBoardInfo(int boardNO);
	
	// 게시글 수
	public int getCount(BoardVO boardVO);
	
	public List<BoardVO> getBoardList(BoardVO boardVo);


}
