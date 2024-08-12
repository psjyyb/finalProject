package com.arion.app.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

public interface AdminService {
	
	List<AdminVO> subListSelect(); // 구독중인 회사목록.
	
	AdminVO subInfoSelect(AdminVO adminVO); // 계약서 상세보기
	
	List<ModuleVO> modListSelect(); // 모듈 목록.
	
	List<AdminVO> endSunListSelect(); // 계약 끝난 목록.
	
	List<QnAVO> qnaListSelect(); // qna 목록.
	
	QnAVO qnaInfoSelect(QnAVO qnaVO); // qna 상세.
	
	int qnaReply(QnAVO qnaVO); // qna 답변
	
	int moduleInsert(@ModelAttribute ModuleVO moduleVO); // 모듈등록 
	
	// 모듈수정화면.
	ModuleVO modSelect(ModuleVO moduleVO); // 모듈정보
	List<ModuleFileVO> modFileSelect(ModuleVO moduleVO); // 모듈 설명사진
	
	int modUpdate(@ModelAttribute ModuleVO moduleVO); // 모듈ㄴ수정
	
	int filesPro(ModuleVO moduleVO); // 다중파일처리
	
	int modDelete(int moduleNo); // 모듈삭제
}
