package com.arion.app.group.main.approval.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.board.service.Criteria;

public interface DocumentService {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
	public int insertDocument(DocumentVO documentVO, HolDocVO holDocVO, List<Integer> approverIds, List<Integer> referenceIds, MultipartFile[] files, String companyCode);

	//결재대기 리스트
	List<DocumentVO> apprWaitList(DocAccessVO docAccessVO, Criteria criteria);
	public int countApprWaitList(DocAccessVO docAccessVO, Criteria criteria);
	
	//결재진행중인 리스트
	List<DocumentVO> apprProgressList(DocAccessVO docAccessVO, Criteria criteria);
	public int countApprProgressList(DocAccessVO docAccessVO, Criteria criteria);

	//결재완료 리스트
	List<DocumentVO> apprFinishList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprFinishList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
		
	//반려 리스트
	List<DocumentVO> apprRejectList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprRejectList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	
	
	public DocumentVO documentInfo(DocumentVO documentVO);
	
	public void updateApprStatus(@Param("docNo") int docNo, @Param("companyCode") String companyCode, int employeeNo, String signImg);

	public void updateRejectStatus(@Param("docNo") int docNo, @Param("companyCode") String companyCode, int employeeNo, String apprReason);
}
