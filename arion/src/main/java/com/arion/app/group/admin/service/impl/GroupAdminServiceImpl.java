package com.arion.app.group.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;

@Service
public class GroupAdminServiceImpl implements GroupAdminService{

	private GroupAdminMapper gaMapper;
	
	public GroupAdminServiceImpl(GroupAdminMapper groupAdminMapper) {
		this.gaMapper = groupAdminMapper;
	}
	
	@Override
	public List<EmployeeVO> empListSelect(String companyCode) {
		return gaMapper.selectempList(companyCode);
	}

}
