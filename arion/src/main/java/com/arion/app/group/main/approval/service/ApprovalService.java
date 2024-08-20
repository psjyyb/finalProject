package com.arion.app.group.main.approval.service;

import java.util.List;

import com.arion.app.common.service.EmployeesVO;

public interface ApprovalService {
	public int insertApproval(List<Integer> approverIds, int DocNo, String companyCode);
	
	public ApprovalVO apprInfo(ApprovalVO approvalVO);
}
