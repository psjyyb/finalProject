package com.arion.app.group.admin.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.group.admin.service.DepartmentListVO;
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
	public int saveDept(DepartmentListVO list, String companyCode) {
		System.out.println(list);
		int result = gaMapper.deptDeSave(companyCode);
		List<DepartmentVO> departments = list.getDepartments();

		for (DepartmentVO deptVO : departments) {
			System.out.println("Department Name: " + deptVO.getDepartmentName());
			System.out.println("Manager ID: " + deptVO.getManagerId());
			deptVO.setCompanyCode(companyCode);
			result += gaMapper.deptInSave(deptVO);
		}
		return result;
	}

	@Transactional
	@Override
	public int saveRank(RankVO rankVO, String companyCode) {
		int result = gaMapper.rankDeSave(companyCode);
		String ranks = rankVO.getRankName();
		String rankN = rankVO.getRankRangkings();
		String rankNarr[] = rankN.split(",");
		String rankArr[] = ranks.split(",");
		List<String> list = Arrays.asList(rankArr);
		RankVO rvo = new RankVO();
		AtomicInteger indexHolder = new AtomicInteger();
		list.forEach(i -> {
			rvo.setRankName(i);
			rvo.setCompanyCode(companyCode);
			rvo.setRankRangkingQ(rankNarr[indexHolder.getAndIncrement()]);
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
		if (comPw != null && comPw != "") {
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
		if (result <= 1) {
			url = "redirect:/groupAdmin/conCan";
		} else {
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

	@Override
	public Map<String, Object> extendContract(int period, String comCode) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		GroupAdminVO gvo = gaMapper.selectSubInfo(comCode);
		Date date = gvo.getFinalDate();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate newDate = localDate.plusMonths(period);

		System.out.println(newDate + "갱신된 날짜가 어떻게 나오는지 ");
		int result = gaMapper.updateContract(newDate, gvo.getContractNo());
		if (result > 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		return map;
	}
	@Override
	public boolean checkOverlapId(String Comcode, String employeeId) {
		Map<String,Object> map = new HashMap<>();
		boolean isSuccess = false;
		EmployeeVO evo = gaMapper.checkOverlapId(Comcode, employeeId);
		System.out.println(evo+"@@@@@@@@@@@@@@@@@@@@@@@");
		if(evo==null) {
			isSuccess=true;
		}
		return isSuccess;
	}
}
