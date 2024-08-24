package com.arion.app.group.main.group.service;

import java.util.List;
import java.util.Map;

import com.arion.app.common.service.DepartmentsVO;
import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;

public interface GroupService {
	  //부서
	  List<DepartmentsVO> selectDepartment(String companyCode);
	  //사원
	  List<EmployeesVO> selectEmployeeList(String companyCode, String departmentName);
	  //사원 상세
	  EmployeesVO selectEmployeeDetail(int employeeNo);
	  
	  
	  //주소록 회사별
	  List<String> getCompanyNames(String employeeId);
	  //사원
	  List<AddressVO> getAddressList(String companyCode, String companyName,String employeeId);
	  //사원 상세
	  AddressVO getAddressDetail(int addrNo);
	  
	  //주소록 사원 등록
	  void registerAddress(AddressVO address);
	  //주소록 수정
	  Map<String, Object> updateAddress(AddressVO address);
	  //주소록 삭제
	  Map<String, Object> deleteAddress(int addrNo);
	  
	  
	  List<AddressVO> getAddressesByCompanyAndEmployee(String companyName, String employeeId);
	
	  List<DepartmentVO> getOrganizationalChartByCompanyCode(String companyCode);

	  //전체사원조회
	   List<EmployeesVO> getAllEmployees();
}

