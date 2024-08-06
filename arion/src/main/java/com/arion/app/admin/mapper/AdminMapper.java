package com.arion.app.admin.mapper;

import java.util.List;

import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;

public interface AdminMapper {
	
	List<AdminVO> selectsubList(); // 구독중인회사목록.
	
	AdminVO selectSubInfo(AdminVO adminVO); // 계약서 상세보기
	
	List<ModuleVO> selectModList(); // 모듈목록 가져오기.
}
