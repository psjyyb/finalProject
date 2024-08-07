package com.arion.app.home.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.security.service.CompanyVO;

@Mapper
public interface QnaMapper {
	
	List<HomeQnaVO> selectQnAList();
	List<CompanyVO> selectCompany(@Param("companyCode") String companyCode);
	
	public HomeQnaVO selectQnaInfo(HomeQnaVO homeQnaVO);
	public int insertQna(HomeQnaVO homeQnaVO);
	public int updateQna(HomeQnaVO homeQnaVO);
	public int deleteQna(int qnaNo);
	public String selectPw(int qnaNo);
}
