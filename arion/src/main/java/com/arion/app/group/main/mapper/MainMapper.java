package com.arion.app.group.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.main.service.SubModuleVO;
import com.arion.app.group.main.service.getModuleVO;



@Mapper
public interface MainMapper {
    List<getModuleVO> getModules(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
    List<SubModuleVO> getSubModules(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
}