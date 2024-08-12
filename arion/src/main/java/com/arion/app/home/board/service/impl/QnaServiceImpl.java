package com.arion.app.home.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.FileService;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.home.board.mapper.QnaMapper;
import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.home.board.service.QnaService;
import com.arion.app.security.service.CompanyVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QnaServiceImpl implements QnaService {
	@Autowired
	QnaMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	FileService fsvc;

	@Override
	public List<HomeQnaVO> selectQnAList(Criteria criteria) {
//		int pageNum = (criteria.getPageNum() - 1) * criteria.getAmount() + 1;
//		int amount = pageNum + criteria.getAmount() - 1;
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("searchType", criteria.getSearchType());
//		params.put("keyword", criteria.getKeyword());
//		params.put("pageNum", pageNum);
//		params.put("amount", amount);

		return mapper.selectQnAList(criteria);
	}

	@Override
	public int selectQnATotalCount(Criteria criteria) {
//		Map<String, Object> params = new HashMap<>();
//		params.put("searchType", criteria.getSearchType());
//		params.put("keyword", criteria.getKeyword());

		return mapper.selectQnATotalCount(criteria);
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

		if (homeQnaVO.getQnaPw() != null && !homeQnaVO.getQnaPw().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(homeQnaVO.getQnaPw());
			homeQnaVO.setQnaPw(encodedPassword);
		}

		log.debug("게시물 등록 시작");
		int result = mapper.insertQna(homeQnaVO);
		log.debug("게시물 등록 완료: {}", result);

		if (files != null && files.length > 0) {
			log.debug("파일이 존재합니다. 파일 저장을 시작합니다.");
			fsvc.insertFiles(files, "qna", homeQnaVO.getQnaNo(), companyCode);
		} else {
			log.debug("파일이 없습니다. 파일 저장을 건너뜁니다.");
		}

		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateQna(HomeQnaVO homeQnaVO, MultipartFile[] files, String companyCode) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = mapper.updateQna(homeQnaVO);

		if (files != null && files.length > 0) {
			fsvc.updateFiles(files, "qna", homeQnaVO.getQnaNo(), companyCode);
		}
		if (result == 1) {
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
	public boolean selectPw(Integer qnaNo, String password) {
		String pw = mapper.selectPw(qnaNo);
		boolean matches = passwordEncoder.matches(password, pw);
		return matches;

	}

}
