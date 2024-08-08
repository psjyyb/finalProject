package com.arion.app.home.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.security.service.CompanyVO;

public interface QnaService {
	
	List<HomeQnaVO> selectQnAList();
	List<CompanyVO> selectCompany(@Param("companyCode") String companyCode);

	public HomeQnaVO QnaInfo(HomeQnaVO homeQnaVO);
	public int insertQna(HomeQnaVO homeQnaVO, MultipartFile[] files, String companyCode);
	public Map<String, Object> updateQna(HomeQnaVO homeQnaVO, MultipartFile[] files, String companyCode);
	public int deleteQna(int qnaNo);
	public String selectPw(int qnaNo);
}