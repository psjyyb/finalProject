package com.arion.app.group.board.service;

import java.util.List;

public interface BoardService {
	
	// 전체조회
	public List<BoardVO> boardList();
	
	// 글 상세조회
	public BoardVO boardInfo(BoardVO boardVO);
	
	// 글 등록
	public long insertBoard(BoardVO boardVO);
	
	// 글 삭제
	public int deleteBoard(int boardNO);
}
