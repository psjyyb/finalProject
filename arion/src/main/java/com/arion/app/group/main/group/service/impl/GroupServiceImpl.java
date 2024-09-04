package com.arion.app.group.main.group.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.DepartmentsVO;
import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.main.group.mapper.GroupMapper;
import com.arion.app.group.main.group.service.AddressVO;
import com.arion.app.group.main.group.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{

	  @Autowired
	    private GroupMapper mapper;
	  	//부서
	    @Override
	    public List<DepartmentsVO> selectDepartment(String companyCode) {
	        return mapper.selectDepartment(companyCode);
	    }
	    //사원
	    @Override
	    public List<EmployeesVO> selectEmployeeList(String companyCode, String departmentName) {
	        return mapper.selectEmployeeList(companyCode, departmentName);
	    }
	    //사원상세
	    @Override
	    public EmployeesVO selectEmployeeDetail(int employeeNo) {
	        return mapper.selectEmployeeDetail(employeeNo);
	    }
	    //주소록 회사별
	    @Override
	    public List<String> getCompanyNames(String employeeId) {
	        return mapper.selectCompanyNames(employeeId);
	    }
	    //주소록 사원
	    @Override
	    public List<AddressVO> getAddressList(String companyCode, String companyName, String employeeId) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("companyCode", companyCode);
	        params.put("companyName", companyName);
	        params.put("employeeId", employeeId);
	        return mapper.selectAddressList(params);
	    }
	    //주소록 사원상세
	    @Override
	    public AddressVO getAddressDetail(int addrNo) {
	        return mapper.selectAddressDetail(addrNo);
	    }
	    //주소록 등록
	    @Override
	    public void registerAddress(AddressVO address) {
	        mapper.insertAddress(address);
	
	    }
	    @Override
	    public Map<String, Object> updateAddress(AddressVO address) {
	        Map<String, Object> map = new HashMap<>();
	        boolean isSuccessed = false;

	        int result = mapper.updateAddress(address);
	        if (result == 1) {
	            isSuccessed = true;
	        }
	        map.put("result", isSuccessed);
	        map.put("target", address);
	        return map;
	    }

	    @Override
	    public Map<String, Object> deleteAddress(int addrNo) {
	        Map<String, Object> map = new HashMap<>();
	        boolean isSuccessed = false;
	        
	        int result = mapper.deleteAddress(addrNo);
	        if (result == 1) {
	            isSuccessed = true;
	        }
	        map.put("result", isSuccessed);
	        map.put("target", addrNo);
	        return map;
	    }
	    @Override
	    public List<AddressVO> getAddressesByCompanyAndEmployee(String companyName, String employeeId) {
	        return mapper.selectAddressesByCompanyAndEmployee(companyName, employeeId);
	    }

	    @Override
	    public List<DepartmentVO> getOrganizationalChartByCompanyCode(String companyCode) {
	        return mapper.selectOrganizationalChartByCompanyCode(companyCode);
	    }
	    //전체사원조회
	    @Override
	    public List<EmployeesVO> getAllEmployees(String companyCode) {
	        return mapper.getAllEmployees(companyCode);
	    }
	    
	    public List<AddressVO> getAllAddress(){
	    	return mapper.getAllAddress();
	    }
}