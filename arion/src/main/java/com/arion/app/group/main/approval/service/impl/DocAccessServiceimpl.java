package com.arion.app.group.main.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.main.approval.mapper.DocAccessMapper;
import com.arion.app.group.main.approval.service.DocAccessService;
import com.arion.app.group.main.approval.service.DocAccessVO;

@Service

public class DocAccessServiceimpl implements DocAccessService {

	@Autowired
	DocAccessMapper mapper;
	
	@Override
	public void insertAccessApproval(List<Integer> approverIds, int DocNo, String companyCode) {
		for (Integer approverId : approverIds) {
            DocAccessVO docAccessVO = new DocAccessVO();
            docAccessVO.setDocNo(DocNo);
            docAccessVO.setEmployeeNo(approverId);
            docAccessVO.setAccessView(1);
            docAccessVO.setAccessAppr(1);
            docAccessVO.setReference(0);
            docAccessVO.setCompanyCode(companyCode);
            mapper.insertAccess(docAccessVO);
        }
	}

	@Override
	public void insertAccessReference(List<Integer> referenceIds, int DocNo, String companyCode) {
		for (Integer referenceId : referenceIds) {
            DocAccessVO docAccessVO = new DocAccessVO();
            docAccessVO.setDocNo(DocNo);
            docAccessVO.setEmployeeNo(referenceId);
            docAccessVO.setAccessView(1);
            docAccessVO.setAccessAppr(0);
            docAccessVO.setReference(1);
            docAccessVO.setCompanyCode(companyCode);
            mapper.insertAccess(docAccessVO);
        }
    }
	
	@Override
	public void insertAddReference(List<EmployeesVO> referenceList, int DocNo, String companyCode) {
		for (EmployeesVO employee : referenceList) {
            DocAccessVO docAccessVO = new DocAccessVO();
            docAccessVO.setDocNo(DocNo);
            docAccessVO.setEmployeeNo(employee.getEmployeeNo());
            docAccessVO.setAccessView(1);
            docAccessVO.setAccessAppr(0);
            docAccessVO.setReference(1);
            docAccessVO.setCompanyCode(companyCode);
            mapper.insertAccess(docAccessVO);
        }
    }
	
	
	@Override
	public List<EmployeesVO> selectAddReference(int employeeNo, String companyCode) {
		
		return mapper.selectAddReference(employeeNo, companyCode);
	}

	@Override
	public List<DocAccessVO> referenceInfo(DocAccessVO docAccessVO){
		
		return mapper.referenceInfo(docAccessVO);
	}
		
}
