package com.arion.app.admin.service;

import java.util.List;

public interface AdminService {
	
	List<AdminVO> subListSelect(); // 구독중인 회사목록.
	
	AdminVO subInfoSelect(AdminVO adminVO); // 계약서 상세보기
	
	List<ModuleVO> modListSelect(); // 모듈 목록.
	
	List<AdminVO> endSunListSelect(); // 계약 끝난 목록.
	
	List<QnAVO> qnaListSelect(); // qna 목록.
	
	QnAVO qnaInfoSelect(QnAVO qnaVO); // qna 상세.
	
	int qnaReply(QnAVO qnaVO); // qna 답변
}
