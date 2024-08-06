package com.arion.app.admin.service;

import java.util.List;

public interface AdminService {
	
	List<AdminVO> subListSelect(); // 구독중인 회사목록.
	AdminVO subInfoSelect(AdminVO adminVO); // 계약서 상세보기
	List<ModuleVO> modListSelect(); // 모듈 목록.
}
