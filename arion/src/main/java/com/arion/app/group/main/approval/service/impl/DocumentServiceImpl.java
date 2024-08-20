package com.arion.app.group.main.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.common.service.FileService;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.approval.mapper.DocumentMapper;
import com.arion.app.group.main.approval.service.ApprovalService;
import com.arion.app.group.main.approval.service.DocAccessService;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentService;
import com.arion.app.group.main.approval.service.DocumentVO;
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
	public int insertDocument(DocumentVO documentVO, List<Integer> approverIds, List<Integer> referenceIds, MultipartFile[] files, String companyCode) {

		int result = mapper.insertDocument(documentVO);
		log.debug("docNo " + documentVO.getDocNo());
		
		//파일등록
		log.debug("파일 등록");
		if(files != null && files.length > 0) {
			fsvc.insertFiles(files, "document", documentVO.getDocNo(), companyCode);
			log.debug("파일 keyNO: " + documentVO.getDocNo());
		}
		//결재등록
		asvc.insertApproval(approverIds, documentVO.getDocNo(), companyCode);
	
		//접근제한 등록
		dasvc.insertAccessApproval(approverIds, documentVO.getDocNo(), companyCode);
		dasvc.insertAccessReference(referenceIds, documentVO.getDocNo(), companyCode);
		
		//작성자와 동일한 부서원, 관리직, 임원 추가
		List<EmployeesVO> referenceList = dasvc.selectAddReference(documentVO.getEmployeeNo(), companyCode);
		dasvc.insertAddReference(referenceList, documentVO.getDocNo(), companyCode);		
		
		return result;
	}

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

}
