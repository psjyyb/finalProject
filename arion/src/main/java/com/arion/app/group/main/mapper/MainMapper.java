package com.arion.app.group.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.main.service.SubModuleVO;
import com.arion.app.group.main.service.getModuleVO;



@Mapper
public interface MainMapper {
  public  List<getModuleVO> getModules(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
  // 하위 모듈 조회
  public List<SubModuleVO> getSubModules(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
}
