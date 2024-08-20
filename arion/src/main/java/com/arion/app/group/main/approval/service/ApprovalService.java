package com.arion.app.group.main.approval.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface ApprovalService {
	public int insertApproval(List<Integer> approverIds, int DocNo, String companyCode);
	
	public List<ApprovalVO> apprInfo(ApprovalVO approvalVO);
	
	public int checkApproval(int employeeNo, int docNo, String companyCode);
	public void approveDocument(@Param("employeeNo") int employeeNo, @Param("docNo") int docNo, @Param("companyCode") String companyCode);

	public void updateNextLine(int docNo, String companyCode);
	
	public int getApprNo(@Param("employeeNo") int employeeNo, @Param("docNo") int docNo, @Param("companyCode") String companyCode);
}
