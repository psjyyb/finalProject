package com.arion.app.group.board.service;

import java.util.List;
import java.util.Map;

public interface BoardService {
	
	// 자유게시판 freeboard //
	// 전체조회
	public List<BoardVO> boardList();
	
	// 글 상세조회
	public BoardVO boardInfo(BoardVO boardVO);
	
	// 글 등록
	public long insertBoard(BoardVO boardVO);
	
	// 글 수정
	public Map<String, Object> updateBoard(BoardVO boardVO);
	
	// 글 삭제
	public int deleteBoard(int boardNO);
	
	// 조회수
	public int ViewCnt(int BoardVO);
}
