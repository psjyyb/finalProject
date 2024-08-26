package com.arion.app.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.security.service.UserVO;
import com.arion.app.security.service.CompanyVO;

@Mapper
public interface UserMapper {
    UserVO getCompanyUserInfo(@Param("companyCode") String companyCode, @Param("userId") String userId);
    UserVO getEmployeeUserInfo(@Param("companyCode") String companyCode, @Param("userId") String userId);

    int insertCompany(CompanyVO cvo);
    int checkId(@Param("companyId") String companyId);
    String selectCompanyName(@Param("companyCode") String companyCode);
    
    CompanyVO findId (@Param("ceoName") String ceoName, @Param("companyBusinessNumber") long companyBusinessNumber);
    int updatePw (@Param("companyCode") String companyCode, @Param("companyId") String companyId, @Param("companyPw") String companyPw);
    String selectEmail (@Param("companyCode") String companyCode, @Param("companyId") String companyId);

}
