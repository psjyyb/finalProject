package com.arion.app.group.main.approval.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.arion.app.group.main.approval.service.ApprovalVO;

public interface ApprovalMapper {
	public int insertApproval(ApprovalVO approvalVO);
	
	public List<ApprovalVO> apprInfo(ApprovalVO approvalVO);
	
	//사원들의 직급 정보를 가져오는 메소드
	List<Map<String, Object>> getApproverRankInfo(@Param("approverIds") List<Integer> approverIds, @Param("companyCode") String companyCode);
	
	public int checkApproval(int employeeNo, int docNo, String companyCode);
	public void approveDocument(@Param("employeeNo") int employeeNo, @Param("docNo") int docNo, @Param("companyCode") String companyCode);
	public void updateNextLine(int docNo, String companyCode);
	
	public int getApprNo(@Param("employeeNo") int employeeNo, @Param("docNo") int docNo, @Param("companyCode") String companyCode);
}
