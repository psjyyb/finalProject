package com.arion.app.group.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.security.service.CompanyVO;

public interface BoardService {

	// 그룹웨어 회사코드
	List<CompanyVO> selectCompany(@Param("companyCode") String companyCode);
	
	// 조회수
	public int ViewCnt(int BoardVO);

	// 토탈
	public int getTotal(Criteria cri);

	
	// noticeboard //
	// 공지사항 전체조회
	public List<BoardVO> noticeboardList(Criteria cri);

	// 공지사항 글 상세조회
	public BoardVO noticeboardInfo(BoardVO boardVO);

	// 공지사항 글 등록
	public long noticeinsertBoard(BoardVO boardVO, MultipartFile[] files, String compnayCode);

	// 공지사항 글 수정
	public Map<String, Object> noticeupdateBoard(BoardVO boardVO);

	// 공지사항 글 삭제
	public int noticedeleteBoard(int boardNO);

	
	// freeboard //
	// 자유게시판 전체조회
	public List<BoardVO> boardList(Criteria cri);

	// 자유게시판 글 상세조회
	public BoardVO boardInfo(BoardVO boardVO);

	// 자유게시판 글 등록
	public long insertBoard(BoardVO boardVO, MultipartFile[] files, String compnayCode);

	// 자유게시판 글 수정
	public Map<String, Object> updateBoard(BoardVO boardVO, MultipartFile[] files, String companyCode);

	// 자유게시판 글 삭제
	public int deleteBoard(int boardNO);

	
	// deptboard //
	// 부서게시판 전체조회
	public List<BoardVO> deptboardList(Criteria cri);

	// 부서게시판 글 상세조회
	public BoardVO deptboardInfo(BoardVO boardVO);

	// 부서게시판 글 등록
	public long deptinsertBoard(BoardVO boardVO, MultipartFile[] files, String compnayCode);

	// 부서게시판 글 수정
	public Map<String, Object> deptupdateBoard(BoardVO boardVO);

	// 부서게시판 글 삭제
	public int deptdeleteBoard(int boardNO);

}
