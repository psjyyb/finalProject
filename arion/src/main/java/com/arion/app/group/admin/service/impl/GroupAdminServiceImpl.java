package com.arion.app.group.admin.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.admin.service.GroupAdminVO;
import com.arion.app.group.admin.service.PayDetailVO;
import com.arion.app.group.admin.service.PayListVO;
import com.arion.app.group.admin.service.RankVO;
import com.arion.app.security.service.CompanyVO;

@Service
public class GroupAdminServiceImpl implements GroupAdminService {

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
	public int saveDept(List<String> list, String companyCode) {
		int result = gaMapper.deptDeSave(companyCode);
		DepartmentVO dvo = new DepartmentVO();
		list.forEach(i -> {
			dvo.setCompanyCode(companyCode);
			dvo.setDepartmentName(i);
			gaMapper.deptInSave(dvo);
		});
		return result;
	}

	@Transactional
	@Override
	public int saveRank(RankVO rankVO, String companyCode) {
		int result = gaMapper.rankDeSave(companyCode);
		String ranks = rankVO.getRankName();
		String rankArr[] = ranks.split(",");
		List<String> list = Arrays.asList(rankArr);
		RankVO rvo = new RankVO();
		list.forEach(i -> {
			rvo.setRankName(i);
			rvo.setCompanyCode(companyCode);
			gaMapper.rankInSave(rvo);
		});
		return result;
	}

	@Override
	public CompanyVO comSelect(String companyCode) {
		return gaMapper.selectCom(companyCode);
	}

	@Override
	public int saveCompany(CompanyVO companyVO) {
		String comPw = companyVO.getCompanyPw();
		System.out.println(comPw+"TLqkf");
		if (comPw != null && comPw !="") {
			String encodedPassword = passwordEncoder.encode(companyVO.getCompanyPw());
			companyVO.setCompanyPw(encodedPassword);
		}
		return gaMapper.companySave(companyVO);
	}

	@Override
	public Map<String, Object> companyPw(String companyCode, String password) {
		String endcodePw = gaMapper.companyPw(companyCode);
		Map<String, Object> map = new HashMap<>();
		int isSuccessed = 0;
		password = password.replace("\"", "");
		if (passwordEncoder.matches(password, endcodePw)) {
			isSuccessed = 1;
		}
		map.put("result", isSuccessed);
		return map;
	}
	@Override
	public int contractNo(String companyCode) {
		return gaMapper.contractNo(companyCode);
	}
	@Transactional
	@Override
	public String cancleContract(int contractNo, String companyCode) {
		int result = gaMapper.cancleCompany(companyCode);
		result += gaMapper.cancleContract(contractNo, companyCode);
		String url = null;
		if(result <= 1) {
			url = "redirect:/groupAdmin/conCan";
		}else {
			url = "redirect:/home";
		}
		return url;
	}
	@Override
	public List<PayListVO> payList(String comcode) {
		return gaMapper.payList(comcode);
	}
	
	@Override
	public List<PayDetailVO> payDetailInfo(int payNo) {
		return gaMapper.payDetailInfo(payNo);
	}
	@Override
	public PayListVO payInfo(int payNo) {
		return gaMapper.payInfo(payNo);
	}
}
