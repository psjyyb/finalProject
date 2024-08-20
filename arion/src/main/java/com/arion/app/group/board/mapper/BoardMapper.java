package com.arion.app.group.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.security.service.CompanyVO;

public interface BoardMapper {

	// 게시글조회
	public List<BoardVO> selectBoardAll(Criteria criteria);

	// 그룹웨어 회사코드
	List<CompanyVO> selectCompany(@Param("companyCode") String companyCode);

	// 조회수
	public int ViewCnt(int viewCnt);

	// total
	public int getTotal(Criteria cri);

	// noticeboard //
	// 공지사항 글 상세조회
	public BoardVO noticeselectBoardInfo(BoardVO boardVO);

	// 공지사항 글 등록
	public int noticeinsertBoardInfo(BoardVO boardVO);

	// 공지사항 글 수정
	public int noticeupdateBoardInfo(BoardVO boardVO);

	// 공지사항 글 삭제
	public int noticedeleteBoardInfo(int boardNO);

	
	// freeboard //
	// 자유게시판 글 상세조회
	public BoardVO selectBoardInfo(BoardVO boardVO);

	// 자유게시판 글 등록
	public int insertBoardInfo(BoardVO boardVO);

	// 자유게시판 글 수정
	public int updateBoardInfo(BoardVO boardVO);

	// 자유게시판 글 삭제
	public int deleteBoardInfo(int boardNO);

	
	// deptboard //
	// 부서게시판 글 상세조회
	public BoardVO deptselectBoardInfo(BoardVO boardVO);

	// 부서게시판 글 등록
	public int deptinsertBoardInfo(BoardVO boardVO);

	// 부서게시판 글 수정
	public int deptupdateBoardInfo(BoardVO boardVO);

	// 부서게시판 글 삭제
	public int deptdeleteBoardInfo(int boardNO);

}// end
