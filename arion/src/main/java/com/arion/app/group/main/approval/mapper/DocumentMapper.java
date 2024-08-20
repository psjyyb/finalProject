package com.arion.app.group.main.approval.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentVO;

public interface DocumentMapper {
	
	List<String> selectDepartment(@Param("companyCode") String companyCode);
	List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName );
	
	public int insertDocument(DocumentVO documentVO);
	
	List<DocumentVO> apprWaitList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprWaitList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);

	List<DocumentVO> apprProgressList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);
	public int countApprProgressList(@Param("docAccessVO") DocAccessVO docAccessVO, @Param("criteria") Criteria criteria);



}
