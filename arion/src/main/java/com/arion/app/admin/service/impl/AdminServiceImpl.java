package com.arion.app.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arion.app.admin.mapper.AdminMapper;
import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ModuleVO;

@Service
public class AdminServiceImpl implements AdminService{
	
	private AdminMapper adminMapper;
	
	public AdminServiceImpl(AdminMapper adminmapper) {
		this.adminMapper = adminmapper;
	}

	@Override
	public List<AdminVO> subListSelect() {
		return adminMapper.selectsubList();
	}
	@Override
	public AdminVO subInfoSelect(AdminVO adminVO) {
		return adminMapper.selectSubInfo(adminVO);
	}
	@Override
	public List<ModuleVO> modListSelect() {
		return adminMapper.selectModList();
	}

}
