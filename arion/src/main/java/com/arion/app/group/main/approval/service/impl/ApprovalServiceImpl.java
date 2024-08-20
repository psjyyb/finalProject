package com.arion.app.group.main.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.main.approval.mapper.ApprovalMapper;
import com.arion.app.group.main.approval.service.ApprovalService;
import com.arion.app.group.main.approval.service.ApprovalVO;

@Service
public class ApprovalServiceImpl implements ApprovalService {
	
	@Autowired
	ApprovalMapper mapper;
	
	@Override
	public int insertApproval(List<Integer> approverIds, int DocNo, String companyCode) {
		 for (int i = 0; i < approverIds.size(); i++) {
	            ApprovalVO approvalVO = new ApprovalVO();
	            approvalVO.setDocNo(DocNo);
	            approvalVO.setEmployeeNo(approverIds.get(i));
	            approvalVO.setSequence(i + 1);
	            approvalVO.setCompanyCode(companyCode);
	            mapper.insertApproval(approvalVO);
	        }
		return 1;
	}

	@Override
	public ApprovalVO apprInfo(ApprovalVO approvalVO) {
		
		return mapper.apprInfo(approvalVO);
	}

}
