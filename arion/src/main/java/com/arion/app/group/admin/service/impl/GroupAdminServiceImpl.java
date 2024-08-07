package com.arion.app.group.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.RankVO;

@Service
public class GroupAdminServiceImpl implements GroupAdminService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private GroupAdminMapper gaMapper;
	
	public GroupAdminServiceImpl(GroupAdminMapper groupAdminMapper) {
		this.gaMapper = groupAdminMapper;
	}
	
	@Override
	public List<EmployeeVO> empListSelect(String companyCode) {
		return gaMapper.selectempList(companyCode);
	}
	@Override
	public int empInsert(EmployeeVO empVO) {
		String encodedPassword = passwordEncoder.encode(empVO.getEmployeePw());
        empVO.setEmployeePw(encodedPassword);
        int result = gaMapper.insertEmp(empVO);
		return result;
	}
	@Override
	public int empUpdate(EmployeeVO empVO) {
		return 0;
	}
	@Override
	public List<DepartmentVO> deptListSelect(String companyCode) {
		return gaMapper.selectDeptList(companyCode);
	}
	@Override
	public List<RankVO> rankListSelect(String companyCode) {
		return gaMapper.selectRankList(companyCode);
	}
}
