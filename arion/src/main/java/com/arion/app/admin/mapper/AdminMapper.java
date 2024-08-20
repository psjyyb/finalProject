package com.arion.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ChartVO;
import com.arion.app.admin.service.ModuleFileVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;

@Mapper
public interface AdminMapper {

	List<AdminVO> selectsubList(); // 구독중인회사목록.

	AdminVO selectSubInfo(AdminVO adminVO); // 계약서 상세보기

	List<ModuleVO> selectModList(); // 모듈목록 가져오기.

	List<AdminVO> selectEndSubList(); // 계약기간 끝난 목록.

	List<QnAVO> selectQnAList(); // QnA 리스트

	QnAVO selectQnAInfo(QnAVO qnaVO); // QnA 상세

	int insertReply(QnAVO qnaVO); // QnA 답변

	int insertModule(ModuleVO moduleVO); // 모듈등록

	int insertModuleFile(ModuleFileVO moduleFileVO); // 모듈파일등록

	// 모듈수정화면.
	ModuleVO selectMod(ModuleVO moduleVO); // 모듈정보

	List<ModuleFileVO> selectModFile(ModuleVO moduleVO); // 모듈 설명사진

	int updateMod(ModuleVO moduleVO); // 모듈정보 수정

	int deleteModFile(ModuleVO moduleVO); // 모듈 사진 삭제

	int deleteMod(int moduleNo); // 모듈삭제

	List<ChartVO> pieChart(); // 파이차트

	List<ChartVO> areaChart(); // 도넛차트

}
