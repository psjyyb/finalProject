package com.arion.app.home.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.board.service.Criteria;
import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.security.service.CompanyVO;

@Mapper
public interface QnaMapper {
	
//	List<HomeQnaVO> selectQnAList(Map<String, Object> params);
//	int selectQnATotalCount(Map<String, Object> params);
	List<HomeQnaVO> selectQnAList(Criteria criteria);
	int selectQnATotalCount(Criteria criteria);
	List<CompanyVO> selectCompany(@Param("companyCode") String companyCode);
	
	public HomeQnaVO selectQnaInfo(HomeQnaVO homeQnaVO);
	public int insertQna(HomeQnaVO homeQnaVO);
	public int updateQna(HomeQnaVO homeQnaVO);
	public int deleteQna(int qnaNo);
	public String selectPw(int qnaNo);
}
