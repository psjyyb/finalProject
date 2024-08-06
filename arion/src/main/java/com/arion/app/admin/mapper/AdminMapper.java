package com.arion.app.admin.mapper;

import java.util.List;

import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;

public interface AdminMapper {
	
	List<AdminVO> selectsubList(); // 구독중인회사목록.
	
	AdminVO selectSubInfo(AdminVO adminVO); // 계약서 상세보기
	
	List<ModuleVO> selectModList(); // 모듈목록 가져오기.
	
	List<AdminVO> selectEndSubList(); // 계약기간 끝난 목록.
	
	List<QnAVO> selectQnAList(); // QnA 리스트
	
	QnAVO selectQnAInfo(QnAVO qnaVO); // QnA 상세
	
	int insertReply(QnAVO qnaVO); // QnA 답변
}
