package com.arion.app.group.main.group.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arion.app.common.service.DepartmentsVO;
import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.admin.service.DepartmentVO;
import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.main.group.service.AddressVO;

public interface GroupMapper {
    // 특정 회사의 모든 부서 목록
    List<DepartmentsVO> selectDepartment(@Param("companyCode") String companyCode);

    // 특정 부서에 속한 직원 목록을 
    List<EmployeesVO> selectEmployeeList(@Param("companyCode") String companyCode, @Param("departmentName") String departmentName);
   
    // 사원상세정보
    EmployeesVO selectEmployeeDetail(@Param("employeeNo") int employeeNo);
    
    // 주소록 회사별 
    List<String> selectCompanyNames(@Param("employeeId")String companyCode);

    // 특정 회사의 주소록 목록 조회
    List<AddressVO> selectAddressList(Map<String, Object> params);

    // 특정 주소록 항목 조회
    AddressVO selectAddressDetail(int addrNo);
    
    // 주소록 등록
    public int insertAddress(AddressVO addressVO);
    
    public int updateAddress(AddressVO addressVO);

    // 주소록 삭제
    public int deleteAddress(int addrNo);
    //사원아이디불러오기
    List<AddressVO> getAddressesByCompanyAndEmployee(String companyName, String employeeId);
    //주소록 이메일불러오기
    List<DepartmentVO> getOrganizationalChartByCompanyCode(String companyCode);
    
    List<AddressVO> selectAddressesByCompanyAndEmployee(String companyName, String employeeId);
    List<DepartmentVO> selectOrganizationalChartByCompanyCode(String companyCode);
    //전체사원조회
    List<EmployeesVO> getAllEmployees();
    //전체 주소록조회
    List<AddressVO> getAllAddress();
}
