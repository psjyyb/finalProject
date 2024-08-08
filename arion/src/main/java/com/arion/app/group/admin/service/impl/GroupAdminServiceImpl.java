package com.arion.app.group.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.GroupAdminVO;
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
		return result == 1 ? empVO.getEmployeeNo() : -1;
	}
	@Override
	public int empUpdate(EmployeeVO empVO) {
		String encodedPassword = passwordEncoder.encode(empVO.getEmployeePw());
        empVO.setEmployeePw(encodedPassword);
		int result = gaMapper.updateEmp(empVO);
		return result == 1 ? empVO.getEmployeeNo() : -1;
	}
	 @Override
	public EmployeeVO empInfoSelect(EmployeeVO empVO) {
		return gaMapper.selectEmpInfo(empVO);
	}
	@Override
	public List<DepartmentVO> deptListSelect(String companyCode) {
		return gaMapper.selectDeptList(companyCode);
	}
	@Override
	public List<RankVO> rankListSelect(String companyCode) {
		return gaMapper.selectRankList(companyCode);
	}
	@Override
	public int empDelete(EmployeeVO empVO) {
		return gaMapper.deleteEmp(empVO);
	}
	@Override
	public GroupAdminVO userCntSelect(String companyCode) {
		return gaMapper.selectUserCnt(companyCode);
	}
	@Override
	public List<GroupAdminVO> endSubSelect(String companyCode) {
		return gaMapper.selectEndSub(companyCode);
	}
	@Override
	public GroupAdminVO sunInfoSelect(String companyCode) {
		return gaMapper.selectSubInfo(companyCode);
	}
	@Transactional
	@Override
	public int saveDept(List<String> list,String companyCode) {
		int result= gaMapper.deptDeSave(companyCode);
		DepartmentVO dvo = new DepartmentVO();
		list.forEach( i ->{
			dvo.setCompanyCode(companyCode);
			dvo.setDepartmentName(i);
			 gaMapper.deptInSave(dvo);
		});
		return result;
	}
	@Transactional
	@Override
	public int saveRank(List<String> list, String companyCode) {
		int result = gaMapper.rankDeSave(companyCode);
		RankVO rvo = new RankVO();
		list.forEach(i ->{
			rvo.setRankName(i);
			rvo.setCompanyCode(companyCode);
			gaMapper.rankInSave(rvo);
		});
		return result;
	}
}
