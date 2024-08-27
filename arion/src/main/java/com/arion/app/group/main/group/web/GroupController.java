package com.arion.app.group.main.group.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.common.service.DepartmentsVO;
import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.main.group.service.AddressVO;
import com.arion.app.group.main.group.service.GroupService;

@Controller
@RequestMapping("/group/Gang")
public class GroupController {

    @Autowired
    GroupService gsvc;

    @GetMapping("/gangSword")
    public String showGangSwordPage(Model model, HttpSession session) {
        String companyCode = (String) session.getAttribute("companyCode");
        List<DepartmentsVO> departments = gsvc.selectDepartment(companyCode);
        model.addAttribute("departments", departments);
        return "group/Gang/gangSword";
    }

    // 부서 목록 불러오기
    @GetMapping("/departments")
    @ResponseBody
    public List<DepartmentsVO> getDepartments(HttpSession session) {
        String companyCode = (String) session.getAttribute("companyCode");
        return gsvc.selectDepartment(companyCode);
    }

    // 직원 목록 불러오기
    @GetMapping("/employees")
    @ResponseBody
    public List<EmployeesVO> getEmployees(@RequestParam String departmentName, HttpSession session) {
        String companyCode = (String) session.getAttribute("companyCode");
        return gsvc.selectEmployeeList(companyCode, departmentName);
    }

    // 직원 상세정보
    @GetMapping("/employee")
    @ResponseBody
    public EmployeesVO getEmployeeDetail(@RequestParam int employeeNo) {
        return gsvc.selectEmployeeDetail(employeeNo);
    }

    // 회사 목록 불러오기
    @GetMapping("/companyNames")
    @ResponseBody
    public List<String> getCompanyNames(HttpSession session) {
        String employeeId = (String) session.getAttribute("loginId");
        return gsvc.getCompanyNames(employeeId);
    }

    // 주소록 목록 불러오기
    @GetMapping("/addresses")
    @ResponseBody
    public List<AddressVO> getAddressList(@RequestParam String companyName, HttpSession session) {
        String companyCode = (String) session.getAttribute("companyCode");
        String employeeId = (String) session.getAttribute("loginId");  // 추가
        return gsvc.getAddressList(companyCode, companyName, employeeId);
    }

    // 주소록 항목 상세정보 불러오기
    @GetMapping("/addressDetail")
    @ResponseBody
    public AddressVO getAddressDetail(@RequestParam int addrNo) {
        return gsvc.getAddressDetail(addrNo);
    }

    // 주소록 HTML 페이지를 보여주는 메서드
    @GetMapping("/address")
    public String showAddressPage() {
        return "group/Gang/address";
    }
    
    //주소록 등록-페이지
    @GetMapping("/AddrInsert")
    public String AddressInsertForm() {
    	return "group/Gang/addrInsert";
    }
    //주소록 등록 - 처리
    @PostMapping("/AddrInsert")
    @ResponseBody
    public String addressInsert(
        @RequestParam String addrName,
        @RequestParam String addrPhone,
        @RequestParam String companyName,
        @RequestParam(required = false) String addrEmail,
        @RequestParam(required = false) String addrRank,
        HttpSession session,
        Model model
    ) {
        String employeeId = (String) session.getAttribute("loginId");
        AddressVO newAddress = new AddressVO();
        newAddress.setAddrName(addrName);
        newAddress.setAddrPhone(addrPhone);
        newAddress.setCompanyName(companyName);
        newAddress.setAddrEmail(addrEmail);
        newAddress.setAddrRank(addrRank);
        newAddress.setEmployeeId(employeeId);
        System.out.println(employeeId+"ddddddddddddddddddddddddddddddd");
        try {
            gsvc.registerAddress(newAddress);
            model.addAttribute("message", "주소록이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "주소록 등록에 실패하였습니다.");
        }

        return "group/Gang/addrInsert"; // 
    }
    //주소록 수정페이지
    @GetMapping("/addressEdit")
    public String showAddressEditPage(@RequestParam int addrNo, Model model) {
        AddressVO address = gsvc.getAddressDetail(addrNo);
        model.addAttribute("address", address);
        return "group/Gang/addrEdit";
    }

    // 주소록 수정 처리
    @PostMapping("/addressEdit")
    @ResponseBody
    public Map<String, Object> addressEdit(@RequestBody AddressVO addressVO) {
        return gsvc.updateAddress(addressVO);
   
    }
    
    // 주소록 삭제 처리
    @PostMapping("/addressDelete")
    @ResponseBody
    public Map<String, Object> addressDelete(@RequestParam int addrNo) {
        return gsvc.deleteAddress(addrNo);
    }
    // 전체 사원 조회
    @GetMapping("/employees/all")
    @ResponseBody
    public List<EmployeesVO> getAllEmployees() {
        return gsvc.getAllEmployees();
    }

}
