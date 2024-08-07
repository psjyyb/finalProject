package com.arion.app.security.mapper;

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

}
