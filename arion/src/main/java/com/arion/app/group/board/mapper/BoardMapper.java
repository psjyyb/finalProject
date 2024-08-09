package com.arion.app.group.board.mapper;

import java.util.List;

import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;

public interface BoardMapper {

	// 자유게시판 freeboard //
	// 전체조회
	public List<BoardVO> selectBoardAll(Criteria criteria);
	
	// 글 상세조회
	public BoardVO selectBoardInfo(BoardVO boardVO);

	// 글 등록
	public int insertBoardInfo(BoardVO boardVO);
	
	// 글 수정
	public int updateBoardInfo(BoardVO boardVO);
	
	// 글 삭제
	public int deleteBoardInfo(int boardNO);

	// 조회수
	public int ViewCnt(int viewCnt);
	
	// total
	public int getTotal(Criteria cri);

	
	
	
	// 공지사항 notice //
	
	
	// 부서게시판 deptboard //
	
	
}//end
