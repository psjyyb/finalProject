package com.arion.app.group.main.approval.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.common.service.FileService;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.approval.mapper.DocumentMapper;
import com.arion.app.group.main.approval.service.ApprovalService;
import com.arion.app.group.main.approval.service.ApprovalVO;
import com.arion.app.group.main.approval.service.DocAccessService;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentService;
import com.arion.app.group.main.approval.service.DocumentVO;
import com.arion.app.group.main.approval.service.HolDocService;
import com.arion.app.group.main.approval.service.HolDocVO;
import com.arion.app.group.main.approval.service.SignService;
import com.arion.app.group.main.approval.service.SignVO;
import com.arion.app.home.board.service.impl.QnaServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	DocumentMapper mapper;
	
	@Autowired
	ApprovalService asvc;
	
	@Autowired
	DocAccessService dasvc;
	
	@Autowired
	FileService fsvc;
	
	@Autowired
	SignService ssvc;
	
	@Autowired
	HolDocService hdsvc;
	
	@Override
	public List<String> selectDepartment(String companyCode) {
		
		return mapper.selectDepartment(companyCode);
	}

	@Override
	public List<EmployeesVO> selectEmployeeList(String companyCode, String departmentName) {
		// TODO Auto-generated method stub
		return mapper.selectEmployeeList(companyCode, departmentName);
	}

	@Transactional
	@Override
	public int insertDocument(DocumentVO documentVO, HolDocVO holDocVO, List<Integer> approverIds, List<Integer> referenceIds, MultipartFile[] files, String companyCode) {
		try {					
			int result = mapper.insertDocument(documentVO);
			log.debug("docNo " + documentVO.getDocNo());
			
			//결재등록
			log.debug("결재 등록 시작. docNo: " + documentVO.getDocNo());
			asvc.insertApproval(approverIds, documentVO.getDocNo(), companyCode);
			log.debug("결재 등록 완료.");
		
			//파일등록
			log.debug("파일 등록");
			if(files != null && files.length > 0) {
				log.debug("파일이 존재합니다. 파일 저장을 시작합니다.");
				log.debug("docNo " + documentVO.getDocNo());
				fsvc.insertFiles(files, "document", documentVO.getDocNo(), companyCode);
				log.debug("파일 keyNO: " + documentVO.getDocNo());
			}

			//접근제한 등록
			dasvc.insertAccessApproval(approverIds, documentVO.getDocNo(), companyCode);
			dasvc.insertAccessReference(referenceIds, documentVO.getDocNo(), companyCode);
		
			//작성자와 동일한 부서원, 임원 추가
			List<EmployeesVO> referenceList = dasvc.selectAddReference(documentVO.getEmployeeNo(), companyCode);
			dasvc.insertAddReference(referenceList, documentVO.getDocNo(), companyCode);		
			
			//휴가신청서 데이터 추가
			if("휴가신청서".equals(documentVO.getDocName())) {
				holDocVO.setDocNo(documentVO.getDocNo());
				hdsvc.insertHolDoc(holDocVO);
				
			}

			return result;
			
		}catch (Exception err) {
	        log.error("Error", err);
	        throw err; 
		}
			
	}
	//결재대기 리스트
	@Override
	public List<DocumentVO> apprWaitList(DocAccessVO docAccessVO, Criteria criteria) {
		List<DocumentVO> apprWait = mapper.apprWaitList(docAccessVO, criteria);
		return apprWait;
	}

	@Override
	public int countApprWaitList(DocAccessVO docAccessVO, Criteria criteria) {
		
		return mapper.countApprWaitList(docAccessVO, criteria);
	}
	
	//결재진행중 리스트
	@Override
	public List<DocumentVO> apprProgressList(DocAccessVO docAccessVO, Criteria criteria) {
		List<DocumentVO> apprProgress = mapper.apprProgressList(docAccessVO, criteria);
		return apprProgress;
	}

	@Override
	public int countApprProgressList(DocAccessVO docAccessVO, Criteria criteria) {
		return mapper.countApprProgressList(docAccessVO, criteria);
	}

	@Override
	public DocumentVO documentInfo(DocumentVO documentVO) {
		return mapper.documentInfo(documentVO);
	}

	@Override
	@Transactional
	public void updateApprStatus(int docNo, String companyCode, int employeeNo, String signImg) {
		
		//결재자 결재완료 등록
		asvc.approveDocument(employeeNo, docNo, companyCode);
		
		//다음 결재 순번 추가
		asvc.updateNextLine(docNo, companyCode);
		
		//서명 FK값 조회
		int apprNo = asvc.getApprNo(employeeNo, docNo, companyCode);
		
		//서명 등록
		SignVO signVO = new SignVO();
		signVO.setSignImg(signImg);
		signVO.setApprNo(apprNo); 
		signVO.setDocNo(docNo);
		signVO.setEmployeeNo(employeeNo);
		signVO.setCompanyCode(companyCode);
		ssvc.apprSign(signVO);
		
		//문서 결재상태 변경
		mapper.updateApprStatus(docNo, companyCode);
		
		// 모든 결재가 완료되었는지 확인 후, 문서 상태를 '결재완료'로 업데이트
	    finalizeApproval(docNo, companyCode, employeeNo);
		
	}

	@Override
	@Transactional
	public void updateRejectStatus(int docNo, String companyCode, int employeeNo, String apprReason) {
		
		//문서 반려상태 등록
		mapper.updateRejectStatus(docNo, companyCode);
		
		//결재자 반려상태 등록
		asvc.rejectDocument(docNo, companyCode, employeeNo, apprReason);
		
		//다음 결재자 미결 처리
		asvc.nextApprStatus(docNo, companyCode, employeeNo);
				
	}
	
	public void finalizeApproval(int docNo, String companyCode, int employeeNo) {
	    // 현재 결재자의 결재를 완료 처리
		asvc.approveDocument(employeeNo, docNo, companyCode);

	    // 남아있는 결재자들이 모두 결재완료 상태인지 확인하고, 문서 상태를 '결재완료'로 업데이트
	    mapper.updateDocumentStatusAllApproved(docNo, companyCode);
	}

	@Override
	public List<DocumentVO> apprFinishList(DocAccessVO docAccessVO, Criteria criteria) {
		List<DocumentVO> apprFinish = mapper.apprFinishList(docAccessVO, criteria);
		return apprFinish;
	}

	@Override
	public int countApprFinishList(DocAccessVO docAccessVO, Criteria criteria) {
		
		return mapper.countApprFinishList(docAccessVO, criteria);
	}

	@Override
	public List<DocumentVO> apprRejectList(DocAccessVO docAccessVO, Criteria criteria) {
		List<DocumentVO> apprReject = mapper.apprRejectList(docAccessVO, criteria);
		return apprReject;
	}

	@Override
	public int countApprRejectList(DocAccessVO docAccessVO, Criteria criteria) {
		return mapper.countApprRejectList(docAccessVO, criteria);
	}

}
