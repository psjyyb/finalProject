package com.arion.app.group.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.arion.app.group.main.mapper.MainMapper;
import com.arion.app.group.main.service.MainService;
import com.arion.app.group.main.service.SubModuleVO;
import com.arion.app.group.main.service.getModuleVO;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;

    @Override
  
    public List<getModuleVO> getmoduleList(getModuleVO getmoduleVO) {
        // 상위 모듈 리스트 조회
        List<getModuleVO> modules = mainMapper.getModules(getmoduleVO.getCompanyCode(), getmoduleVO.getEmployeeId());
        // 하위 모듈 리스트 조회
        List<SubModuleVO> subModules = mainMapper.getSubModules(getmoduleVO.getCompanyCode(), getmoduleVO.getEmployeeId());

        // 상위 모듈과 하위 모듈을 조합
        for (getModuleVO module : modules) {
            module.setSubModules(new ArrayList<>());
            for (SubModuleVO subModule : subModules) {
                if (module.getModuleNo() != null && subModule.getModuleNo() != null 
                        && subModule.getModuleNo().equals(module.getModuleNo())) {
                    module.getSubModules().add(subModule);
                }
            }
        }
        return modules;
    }
}