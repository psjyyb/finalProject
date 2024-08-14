package com.arion.app.group.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.FileService;
import com.arion.app.group.board.mapper.BoardMapper;
import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.security.service.CompanyVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

	private BoardMapper boardMapper;
	
	@Autowired
	FileService fsvc;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	// 자유게시판 //
	// 전체 게시글 조회
	@Override
	public List<BoardVO> boardList(Criteria cri) {
		return boardMapper.selectBoardAll(cri);
	}
	
	// 회사코드?
	@Override
	public List<CompanyVO> selectCompany(String companyCode) {
		return boardMapper.selectCompany(companyCode);
	}

	// 게시글 상세조회
	@Override
	public BoardVO boardInfo(BoardVO boardVO) {
		return boardMapper.selectBoardInfo(boardVO);
	}

	// 게시글 등록
	@Transactional
	@Override
	public long insertBoard(BoardVO boardVO, MultipartFile[] files, String companyCode) {
		long result = boardMapper.insertBoardInfo(boardVO);
		
		if (files != null && files.length > 0) {
			log.debug("파일이 존재합니다. 파일 저장을 시작합니다.");
			fsvc.insertFiles(files, "board", boardVO.getBoardNo(), companyCode);
		} else {
			log.debug("파일이 없습니다. 파일 저장을 건너뜁니다.");
		}
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

	// 토탈건수
	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotal(cri);
	}


}
