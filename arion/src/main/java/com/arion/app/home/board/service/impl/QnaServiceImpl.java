package com.arion.app.home.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.FileService;
import com.arion.app.home.board.mapper.QnaMapper;
import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.home.board.service.QnaService;
import com.arion.app.security.service.CompanyVO;

@Service
public class QnaServiceImpl implements QnaService{
	@Autowired
	QnaMapper mapper;
	
	@Autowired
	FileService fsvc;
	
	@Override
	public List<HomeQnaVO> selectQnAList() {
		
		return mapper.selectQnAList();
	}

	@Override
	public List<CompanyVO> selectCompany(String companyCode) {
		
		return mapper.selectCompany(companyCode);
	}

	@Override
	public HomeQnaVO QnaInfo(HomeQnaVO homeQnaVO) {
		
		return mapper.selectQnaInfo(homeQnaVO);
	}

	@Transactional
    @Override
    public int insertQna(HomeQnaVO homeQnaVO, MultipartFile[] files, String companyCode) {
        int result = mapper.insertQna(homeQnaVO);

        if (files != null && files.length > 0) {
            fsvc.insertFiles(files, "qna", homeQnaVO.getQnaNo(), companyCode);
        }

        return result;
    }

	@Override
	public Map<String, Object> updateQna(HomeQnaVO homeQnaVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = mapper.updateQna(homeQnaVO);
		if(result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", homeQnaVO);
		return map;
	}

	@Override
	public int deleteQna(int qnaNo) {
		
		return mapper.deleteQna(qnaNo);
	}

	@Override
	public String selectPw(int qnaNo) {
		
		return mapper.selectPw(qnaNo);
	}

}
