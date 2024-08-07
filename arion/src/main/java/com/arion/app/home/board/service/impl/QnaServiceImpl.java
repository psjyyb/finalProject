package com.arion.app.home.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.home.board.mapper.QnaMapper;
import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.home.board.service.QnaService;

@Service
public class QnaServiceImpl implements QnaService{
	@Autowired
	QnaMapper mapper;
	
	@Override
	public List<HomeQnaVO> selectQnAList() {
		
		return mapper.selectQnAList();
	}

}
