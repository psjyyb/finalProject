package com.arion.app.group.main.approval.service;

import java.util.List;

import com.arion.app.common.service.EmployeesVO;

public interface DocAccessService {
	public void insertAccessApproval(List<Integer> approverIds, int DocNo, String companyCode);
	public void insertAccessReference(List<Integer> referenceIds, int DocNo, String companyCode);
	
	public void insertAddReference(List<EmployeesVO> referenceList, int DocNo, String companyCode);
	
	List<EmployeesVO> selectAddReference(int employeeNo, String companyCode);
	
	public List<DocAccessVO> referenceInfo(DocAccessVO docAccessVO);
}
