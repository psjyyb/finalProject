package com.arion.app.group.main.approval.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentVO;

public interface DocumentMapper {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
	public int insertDocument(DocumentVO documentVO);
	
	List<DocumentVO> apprWaitList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprWaitList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	
	//결재진행중인 리스트
	List<DocumentVO> apprProgressList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprProgressList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);

	//결재완료 리스트
	List<DocumentVO> apprFinishList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprFinishList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	
	//반려 리스트
	List<DocumentVO> apprRejectList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprRejectList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	
	
	
	public DocumentVO documentInfo(DocumentVO documentVO);
	
	public void updateApprStatus(@Param("docNo") int docNo, @Param("companyCode") String companyCode);
	
	public void updateRejectStatus(@Param("docNo") int docNo, @Param("companyCode") String companyCode);

	public void updateDocumentStatusAllApproved(@Param("docNo") int docNo, @Param("companyCode") String companyCode);

}
