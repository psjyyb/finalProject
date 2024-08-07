package com.arion.app.home.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.home.board.service.HomeQnaVO;

@Mapper
public interface QnaMapper {
	
	List<HomeQnaVO> selectQnAList();
	
}
