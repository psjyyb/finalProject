package com.arion.app.group.main.approval.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.board.service.Criteria;

public interface DocumentService {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
	public int insertDocument(DocumentVO documentVO, List<Integer> approverIds, List<Integer> referenceIds, MultipartFile[] files, String companyCode);

	
	List<DocumentVO> apprWaitList(DocAccessVO docAccessVO, Criteria criteria);
	public int countApprWaitList(DocAccessVO docAccessVO, Criteria criteria);
	
	List<DocumentVO> apprProgressList(DocAccessVO docAccessVO, Criteria criteria);
	public int countApprProgressList(DocAccessVO docAccessVO, Criteria criteria);

	public DocumentVO documentInfo(DocumentVO documentVO);
}
