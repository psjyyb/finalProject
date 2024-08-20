package com.arion.app.group.main.approval.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.main.approval.mapper.ApprovalMapper;
import com.arion.app.group.main.approval.service.ApprovalService;
import com.arion.app.group.main.approval.service.ApprovalVO;
import com.arion.app.group.main.approval.service.DocumentService;
import com.arion.app.group.main.approval.service.SignService;
import com.arion.app.group.main.approval.service.SignVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	ApprovalMapper mapper;

	@Override
	public int insertApproval(List<Integer> approverIds, int docNo, String companyCode) {
		// 1. 사원들의 직급 정보를 가져오기

		List<Map<String, Object>> approverRankInfo = mapper.getApproverRankInfo(approverIds, companyCode);

		for (Map<String, Object> approver : approverRankInfo) {
			if (approver.get("RANK_RANKING") == null || approver.get("EMPLOYEE_NO") == null) {
				log.error("approver : " + approver);

				continue;
			}
		}
		// 2. 직급 순위를 기준으로 사원들을 정렬
//		List<Integer> sortedApproverIds = approverRankInfo.stream()
//				.sorted((a, b) -> Integer.compare((int) a.get("RANK_RANKING"), (int) b.get("RANK_RANKING")))
//				.map(a -> (Integer) a.get("EMPLOYEE_NO")).collect(Collectors.toList());

		List<Integer> sortedApproverIds = approverRankInfo.stream().sorted((a, b) -> {
			// BigDecimal로 반환된 rank_ranking을 Integer로 변환
			BigDecimal rankA = (BigDecimal) a.get("RANK_RANKING");
			BigDecimal rankB = (BigDecimal) b.get("RANK_RANKING");
			return rankB.compareTo(rankA);
		}).map(a -> ((BigDecimal) a.get("EMPLOYEE_NO")).intValue()).collect(Collectors.toList());
		log.debug("Sorted Approver IDs: " + sortedApproverIds);

		// 3. 결재자 정보를 DB에 삽입
		for (int i = 0; i < sortedApproverIds.size(); i++) {
			ApprovalVO approvalVO = new ApprovalVO();
			approvalVO.setDocNo(docNo);
			approvalVO.setEmployeeNo(sortedApproverIds.get(i));
			approvalVO.setSequence(i + 1);
			approvalVO.setCompanyCode(companyCode);
			approvalVO.setApprLine(1);
			mapper.insertApproval(approvalVO);
		}
		return 1;
	}

	@Override
	public List<ApprovalVO> apprInfo(ApprovalVO approvalVO) {

		return mapper.apprInfo(approvalVO);
	}

	@Override
	public int checkApproval(int employeeNo, int docNo, String companyCode) {

		return mapper.checkApproval(employeeNo, docNo, companyCode);
	}

	@Override
	public void approveDocument(int employeeNo, int docNo, String companyCode) {
		mapper.approveDocument(employeeNo, docNo, companyCode);
		
	}

	@Override
	public void updateNextLine(int docNo, String companyCode) {
		mapper.updateNextLine(docNo, companyCode);
		
	}

	@Override
	public int getApprNo(int employeeNo, int docNo, String companyCode) {
		// TODO Auto-generated method stub
		return mapper.getApprNo(employeeNo, docNo, companyCode);
	}

	@Override
	public int hasApproved(int employeeNo, int docNo, String companyCode) {
		
		return mapper.hasApproved(employeeNo, docNo, companyCode);
	}

	@Override
	public void rejectDocument(int docNo, String companyCode, int employeeNo, String apprReason) {
		
		mapper.rejectDocument(docNo, companyCode, employeeNo, apprReason);
	}
	
	@Override
	public void nextApprStatus(int docNo, String companyCode, int employeeNo) {
		
		mapper.nextApprStatus(docNo, companyCode, employeeNo);
	}

}
